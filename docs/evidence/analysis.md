# Báo cáo phân tích lỗi CI

## Lỗi 1 — Sai kết quả phí EXPRESS
- File: `src/main/java/com/lab/ShippingCalculator.java`
- Nơi sai: nhánh `EXPRESS`, công thức tính phí
- Log: `docs/evidence/logs/ci-failure-express-1.txt.txt`
- Nguyên nhân: `weight * 5000 + 20000` đã bị lệch 1 đơn vị, dẫn đến `45001.0` thay vì `45000.0`
- Sửa: khôi phục công thức đúng

## Lỗi 2 — Dependency Logback không tồn tại
- File: `pom.xml`
- Nơi sai: dòng `<version>9.9.9</version>` của `logback-classic`
- Log: `docs/evidence/logs/ci-failure-logback-dependency.txt`
- Nguyên nhân: Maven Central không có artifact `ch.qos.logback:logback-classic:9.9.9`
- Sửa: đổi về `1.4.11`

## Lỗi 3 — Tỷ lệ STANDARD bị sai lệch 1 đơn vị
- File: `src/main/java/com/lab/ShippingCalculator.java`
- Nơi sai: nhánh `STANDARD`, dòng `double fee = weight * 3001;`
- Log: `docs/evidence/logs/ci-failure-standard-rate.txt`
- Nguyên nhân: công thức tính phí tiêu chuẩn đã bị cấy thêm 1 đơn vị (`3001` thay vì `3000`), nên với `weight=5.0` trả về `15005.0` thay vì `15000.0`
- Sửa: đổi lại `double fee = weight * 3000;`

## Kết quả sau khi sửa
- Pipeline GitHub Actions đã quay lại xanh sau khi khôi phục logic đúng.
- Tất cả lỗi đã được sửa và lưu bằng chứng trong thư mục `docs/evidence/logs/`.
