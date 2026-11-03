# Báo cáo phân tích lỗi CI

Tài liệu tổng hợp bốn lỗi CI đã tái hiện, log liên quan, nguyên nhân và cách sửa. Mỗi liên kết run mở trực tiếp GitHub Actions để đối chiếu bằng chứng.

## Lỗi 1 — Sai kết quả phí EXPRESS

- **Vị trí:** `src/main/java/com/lab/ShippingCalculator.java`, công thức nhánh EXPRESS tại commit gây lỗi.
- **Run lỗi:** [Java CI #2](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41671773593)
- **Log:** [`ci-failure-express-1.txt.txt`](logs/ci-failure-express-1.txt.txt)
- **Nguyên nhân:** công thức được cố ý làm lệch 1 đơn vị, nên kiểm thử nhận `45001.0` thay vì `45000.0`.
- **Sửa:** khôi phục công thức EXPRESS đúng.

## Lỗi 2 — Phiên bản dependency Logback không tồn tại

- **Vị trí:** `pom.xml`, dòng 18 trong commit gây lỗi — phiên bản `logback-classic`.
- **Run lỗi:** [Java CI #8](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41686090740)
- **Log:** [`ci-failure-logback-dependency.txt`](logs/ci-failure-logback-dependency.txt)
- **Nguyên nhân:** Maven Central không tìm thấy `ch.qos.logback:logback-classic:9.9.9`, khiến Maven không thể phân giải dependency.
- **Sửa:** khôi phục phiên bản `1.4.11`.

## Lỗi 3 — Đơn giá STANDARD bị sai

- **Vị trí:** `src/main/java/com/lab/ShippingCalculator.java`, nhánh STANDARD; ở commit lỗi đơn giá là `3001` thay vì `3000`.
- **Run lỗi:** [Java CI #12](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41689141621)
- **Log:** [`ci-failure-standard-rate.txt`](logs/ci-failure-standard-rate.txt)
- **Nguyên nhân:** với khối lượng `5`, công thức lỗi trả về `15005.0`, trong khi kiểm thử mong đợi `15000.0`.
- **Sửa:** đưa đơn giá về `3000`.

## Lỗi 4 — Null service type gây NullPointerException

- **Vị trí:** `src/main/java/com/lab/ShippingCalculator.java`, dòng 29 tại commit lỗi `e4452d4` — `type.equals(EXPRESS)`.
- **Kiểm thử phát hiện:** `src/test/java/com/lab/ShippingCalculatorTest.java`, dòng 31, truyền `null` và mong đợi `IllegalArgumentException`.
- **Run lỗi:** [Java CI #15](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41691185242)
- **Log:** [`ci-failure-null-service-type.txt`](logs/ci-failure-null-service-type.txt)
- **Nguyên nhân:** gọi `.equals` trên biến `type` khi biến này là `null` ném `NullPointerException`, không đúng hợp đồng phương thức và assertion của test.
- **Sửa:** dùng `EXPRESS.equals(type)`. So sánh theo hướng này an toàn với `null`; giá trị `null` tiếp tục tới nhánh báo `IllegalArgumentException`.

### Trích log lỗi thứ 4

```text
[ERROR] ShippingCalculatorTest.rejectsUnknownOrMissingServiceType:31
Unexpected exception type thrown, expected: <java.lang.IllegalArgumentException>
but was: <java.lang.NullPointerException>
[ERROR] Tests run: 15, Failures: 1, Errors: 0, Skipped: 0
[ERROR] BUILD FAILURE
```

## Trạng thái

Lỗi thứ 4 đã được sửa trong `ShippingCalculator.java`. Hãy xác nhận run GitHub Actions của commit sửa lỗi hoàn tất thành công để kết luận pipeline xanh.
