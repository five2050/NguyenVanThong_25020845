## PHÂN TÍCH LỖI CI – EXPRESS SHIPPING

## 1. Bằng chứng
Ảnh chụp GitHub Actions: images/ci-failure-express.png
Log lỗi: logs/ci-failure-express.txt

## 2. Vị trí lỗi
File kiểm thử: ShippingCalculatorTest.java
Phương thức kiểm thử: calculatesExpressShipping()
Dòng phát hiện lỗi: 19

## 3. Log lỗi từ GitHub Actions
ShippingCalculatorTest.calculatesExpressShipping:19
expected: <45000.0> but was: <45001.0>
Tests run: 15, Failures: 1, Errors: 0, Skipped: 0
BUILD FAILURE

## 4. Giải thích nguyên nhân kỹ thuật
Phương thức tính phí vận chuyển EXPRESS trả về kết quả 45001 thay vì 45000 như giá trị mong đợi trong kiểm thử.
Điều này cho thấy logic tính phí vận chuyển EXPRESS đang có sai lệch 1 đơn vị. Kiểm thử JUnit phát hiện sự không khớp giữa giá trị thực tế và giá trị mong đợi, khiến quá trình Maven Verify thất bại.

## 5. Đề xuất cách sửa

Kiểm tra phương thức tính phí vận chuyển EXPRESS trong file ShippingCalculator.java.
Rà soát công thức và các giá trị được sử dụng trong quá trình tính phí.
Điều chỉnh logic để kết quả trả về đúng 45000 đối với dữ liệu đầu vào của bài kiểm thử.

## 6. Kiểm tra sau khi sửa
Chạy lệnh sau để thực hiện lại quá trình kiểm thử:
mvn clean verify
Kiểm tra kết quả:
Tất cả các bài kiểm thử đều thành công.
Không còn lỗi sai lệch kết quả tính phí EXPRESS.
GitHub Actions hoàn thành thành công (Build Success).