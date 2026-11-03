# Báo cáo phân tích lỗi CI

Tài liệu ghi lại các lỗi đã tái hiện trong GitHub Actions, bằng chứng log, nguyên nhân và kết quả sau khi sửa. Các đường dẫn ảnh bên dưới trỏ tới ảnh đã lưu trong repository; run GitHub Actions là nguồn đối chiếu trực tiếp.

## Lỗi 1 — Sai kết quả phí EXPRESS

| Mục | Chi tiết |
|---|---|
| Vị trí | `src/main/java/com/lab/ShippingCalculator.java`, dòng 25 (công thức EXPRESS tại commit gây lỗi) |
| Kiểm thử phát hiện | `src/test/java/com/lab/ShippingCalculatorTest.java`, dòng 19 — `calculatesExpressShipping()` |
| Run lỗi | [Java CI #2](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41671773593) |
| Bằng chứng ảnh | [`ci-failure-express-2.png.png`](images/ci-failure-express-2.png.png) |
| Bằng chứng log | [`ci-failure-express-1.txt.txt`](logs/ci-failure-express-1.txt.txt) |
| Kết quả sau sửa | [Java CI #3 thành công](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41672880221) |

Log cho thấy kiểm thử kỳ vọng `45000.0` nhưng nhận `45001.0`. Công thức EXPRESS cộng thừa 1 đơn vị nên giá trị thực tế không khớp với mức phí mong đợi. JUnit bắt lỗi tại assertion của kiểm thử; cần sửa công thức tính phí trong `ShippingCalculator` và chạy lại toàn bộ verify.

## Lỗi 2 — Phiên bản dependency Logback không tồn tại

| Mục | Chi tiết |
|---|---|
| Vị trí | `pom.xml`, dòng 18 — phiên bản `logback-classic` |
| Run lỗi và ảnh chụp | [Java CI #8](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41686090740) |
| Run lặp lại do push tiếp theo | [Java CI #9](https://github.com/five2050/NguyenVanThong_25020845/actions/runs/41687441988) |
| Log lưu trong repository | [`ci-failure-logback-dependency.txt`](logs/ci-failure-logback-dependency.txt) |
| Trạng thái sửa | Đã khôi phục phiên bản đã dùng trước đó `1.4.11`; chờ CI xác nhận ở commit sửa lỗi. |

### Trích log GitHub Actions

```text
[WARNING] The POM for ch.qos.logback:logback-classic:jar:9.9.9 is missing, no dependency information available
[ERROR] Failed to execute goal on project week9-bank-system: Could not resolve dependencies for project com.practice.maven:week9-bank-system:jar:1.0-SNAPSHOT
[ERROR] dependency: ch.qos.logback:logback-classic:jar:9.9.9 (compile)
[ERROR]     Could not find artifact ch.qos.logback:logback-classic:jar:9.9.9 in central (https://repo.maven.apache.org/maven2)
[ERROR] BUILD FAILURE
```

**Nguyên nhân kỹ thuật:** Maven cần tải dependency trước khi biên dịch hoặc chạy test. `9.9.9` không có trên Maven Central, nên quá trình phân giải dependency thất bại và build dừng sớm; đây không phải lỗi trong Java source hay test.

**Cách sửa:** đặt lại phiên bản hợp lệ `1.4.11` trong `pom.xml`. Sau push, đợi job `clean verify` hoàn tất và xác nhận toàn bộ matrix CI xanh trước khi tạo lỗi tiếp theo.

> Ảnh chụp do người dùng lưu; trang Actions ở liên kết trên là bằng chứng truy cập được. Nếu cần ảnh hiển thị ngay trong bản preview của Markdown, hãy tải ảnh chụp lên `docs/evidence/images/` rồi cập nhật liên kết tương đối tại đây.
