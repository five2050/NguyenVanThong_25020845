## Tuần 9 - Hệ thống Ngân hàng và Maven CI
Các lớp ngân hàng cũ giờ nằm trong `src/main/java/bank/system` và sử dụng 
Google Java Style. Maven chạy Checkstyle trong giai đoạn `validate`, kiểm thử JUnit 
Jupiter 5.9.2 với Surefire, và JaCoCo trong giai đoạn `verify`; build sẽ fail nếu độ 
bao phủ dòng dưới 80%. Logback Classic 1.4.11 ghi các entry INFO/WARN/ERROR có dấu 
thời gian ra console và `target/logs/bank-system.log`. INFO ghi lại các giao dịch thành 
công và các mốc vòng đời; WARN xác định các tình huống có thể phục hồi; ERROR ghi 
lại các thao tác bị từ chối hoặc lỗi kèm ngoại lệ và stack trace. Các chỗ giữ tham số 
của SLF4J tránh việc nối chuỗi sớm.

Hibernate Core 6.2.0.Final được bao gồm cho việc lưu trữ trong tương lai. 
Artifact thực thi đã được shade là `target/week9-bank-system-1.0-SNAPSHOT.jar`; 
chạy bằng lệnh `java -jar target/week9-bank-system-1.0-SNAPSHOT.jar`. Manifest 
JAR theo quy ước cũng khai báo `bank.system.Bank` là `Main-Class`.

Workflow GitHub Actions sẽ chạy lệnh `clean verify` trên Ubuntu, Windows và macOS cho 
các lần push và pull request nhắm đến nhánh `main`. Bộ nhớ đệm Maven đã được bật. 
Mỗi lần chạy trong ma trận sẽ tải lên file JAR thực thi và báo cáo HTML JaCoCo của nó. 
Bài kiểm tra khách hàng ngân hàng sử dụng `Path.resolve` và thư mục tạm, thay vì một 
đường dẫn cố định theo nền tảng. Pull request cũng nhận được các chú thích Checkstyle 
trực tiếp.

## Vẫn Cần xác minh từ phía GitHub
Workspace cục bộ này không có remote Git và không có nhật ký chạy GitHub Actions, 
vì vậy nó không thể tạo/đẩy repository, tạo ảnh chụp màn hình hoặc bằng chứng nhật ký 
thực thi, so sánh hai lần chạy cache đã lưu trữ, xác minh các bình luận inline đã lưu 
trữ, hoặc cấu hình bảo vệ nhánh. Sau khi đẩy dự án này, hãy cấu hình bảo vệ nhánh cho 
`main` và đánh dấu các kiểm tra `Build (ubuntu-latest)`, `Build (windows-latest)`, và 
`Build (macos-latest)` là cần thiết. Thời gian cache đã lưu trữ và các chú thích PR của 
Checkstyle phải được xác nhận từ những lần chạy đó; không có bằng chứng từ xa nào được 
khẳng định ở đây.

## Bài tập pipeline lỗi (đính kèm ShippingCalculator)
com.lab.ShippingCalculator và các bài kiểm tra JUnit của nó đã được bao gồm trong dự 
án Maven này. Từ ví dụ lỗi được cung cấp, các vấn đề cấu hình cụ thể là phiên bản 
Logback `9.9.9` không tồn tại và nhà cung cấp Surefire `2.12.4` đã lỗi thời, trước khi 
có hỗ trợ JUnit 5. Các khẳng định trong kiểm tra cũng sử dụng so sánh số dấu chấm động
mà không có delta; các bài kiểm tra đã sửa dùng delta zero rõ ràng của JUnit Jupiter. 
Những sửa đổi này được thể hiện trong `pom.xml` và `src/test/java/com/lab/ShippingCalculatorTest.java`.a`.