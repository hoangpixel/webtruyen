# WebTruyen

WebTruyen là một hệ thống website đọc truyện online được xây dựng bằng Java Spring Boot, tập trung vào trải nghiệm người dùng đọc truyện, quản lý danh sách truyện và hỗ trợ các chức năng quản trị cho admin. Dự án kết hợp mô hình MVC, JPA/Hibernate, Spring Security và Thymeleaf để tạo ra một trang web đọc truyện đầy đủ tính năng nhưng vẫn dễ mở rộng.

## Tổng quan dự án

Project này mô phỏng một nền tảng truyện điện tử với các chức năng chính sau:

- Hiển thị danh sách truyện theo trang và top truyện hot
- Tìm kiếm truyện cơ bản và nâng cao theo tên, tác giả, thể loại
- Xem chi tiết truyện, đánh giá sao và bình luận
- Đọc từng chương truyện theo thứ tự
- Quản lý tài khoản người dùng: đăng ký, đăng nhập, cập nhật thông tin cá nhân, avatar
- Lưu truyện yêu thích cho người dùng
- Giao diện quản trị dành cho admin với dashboard thống kê
- Quản lý truyện, chương, thể loại, vai trò, quyền hạn và tài khoản theo phân quyền

Dự án đang dùng MySQL làm hệ quản trị cơ sở dữ liệu, Spring Data JPA để ánh xạ ORM, Thymeleaf để render giao diện, và Spring Security để bảo vệ quyền truy cập.

## Công nghệ sử dụng

| Layer | Technologies |
|---------|-------------|
| Backend | Java 21, Spring Boot 3.2.5, Spring MVC, Spring Data JPA, Hibernate, Spring Security |
| Frontend | Thymeleaf, HTML5, CSS3, JavaScript, Bootstrap 5 |
| Database | MySQL |
| Build Tool | Maven |
| Version Control | Git, GitHub |

## Kiến trúc dự án

Project này tuân theo cấu trúc MVC chuẩn của Spring Boot:

```text
webtruyen/
├── src/
│   ├── main/
│   │   ├── java/com/flogin/webtruyen/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── security/
│   │   │   └── WebtruyenApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

### Vai trò các package chính

- model: chứa các entity như Truyen, Chuong, TheLoai, TaiKhoan, VaiTro, DanhGia, BinhLuan...
- repository: giao tiếp với database thông qua Spring Data JPA
- service: xử lý nghiệp vụ cho từng module
- controller: nhận request và điều hướng dữ liệu tới view
- security: cấu hình xác thực, phân quyền và mã hóa mật khẩu
- templates/static: giao diện người dùng và quản trị

## Mô tả chức năng từng module

### 1. Module Trang chủ & Khám phá truyện

Module này tập trung vào trải nghiệm tiếp cận nội dung của người dùng ngay khi vào website. Trang chủ có nhiệm vụ hiển thị các truyện nổi bật, danh sách truyện mới cập nhật, cùng các tính năng tìm kiếm và định hướng nội dung.

Chức năng chính bao gồm:

- Hiển thị Top 10 truyện hot dựa trên lượt xem và tính nổi bật của bộ truyện
- Hiển thị danh sách truyện mới cập nhật theo phân trang
- Cung cấp bộ lọc tìm kiếm cơ bản theo tên truyện
- Hỗ trợ tìm kiếm nâng cao theo tên, tác giả, và thể loại
- Chuyển hướng người dùng đến trang chi tiết truyện, nơi có thể xem mô tả, thể loại, đánh giá và chương đầu tiên
- Tạo trải nghiệm thân thiện bằng giao diện Bootstrap, responsive và dễ sử dụng

Mục tiêu của module này là giúp người dùng nhanh chóng tìm thấy nội dung phù hợp và tạo cảm giác trực quan, hấp dẫn ngay từ lần đầu truy cập.

### 2. Module Quản lý Truyện

Module truyện là trung tâm của hệ thống, chứa toàn bộ dữ liệu nghiệp vụ về từng bộ truyện.

Các chức năng trọng tâm:

- Thêm mới truyện với thông tin như tên truyện, tác giả, mô tả, ảnh bìa, trạng thái
- Gán nhiều thể loại cho một truyện
- Cập nhật thông tin truyện khi có thay đổi nội dung hoặc bìa ảnh
- Xóa truyện nếu không còn phù hợp với hệ thống
- Hiển thị chi tiết truyện với mô tả, danh sách chương, thể loại và các chỉ số thống kê
- Tính toán điểm đánh giá trung bình, tổng lượt đánh giá, lượt xem
- Hỗ trợ slug hóa tên truyện cho URL thân thiện, ví dụ: tên truyện chuyển thành dạng đường dẫn dễ đọc

Module này đóng vai trò là cốt lõi cho cả phía người dùng lẫn admin, bởi mọi hoạt động đọc truyện và quản trị đều gắn liền với dữ liệu truyện.

### 3. Module Quản lý Chương Truyện

Chương truyện là đơn vị đọc nội dung cụ thể của truyện. Module này cho phép admin quản lý từng chương một cách chi tiết.

Chức năng chính:

- Thêm chương mới cho một truyện cụ thể
- Sắp xếp thứ tự chương theo số chương hoặc thời gian cập nhật
- Cập nhật tiêu đề, nội dung và ảnh minh họa của chương
- Xóa chương không còn cần thiết
- Hiển thị chương mới nhất trên trang chủ và trong giao diện chi tiết truyện
- Hỗ trợ upload hình ảnh cho từng chương, phù hợp với dạng truyện ảnh hoặc truyện có nhiều hình minh họa

Truthy module này giúp hệ thống có thể tổ chức nội dung theo từng phần, tạo trải nghiệm đọc mượt mà cho người dùng và hỗ trợ việc cập nhật nội dung liên tục.

### 4. Module Thể Loại

Module thể loại dùng để phân nhóm truyện theo chủ đề hoặc phong cách.

Chức năng chính:

- Tạo mới thể loại truyện
- Chỉnh sửa tên và mô tả thể loại
- Xóa thể loại khi không còn cần thiết
- Gắn thể loại cho truyện trong quá trình quản trị
- Sử dụng thể loại trong tìm kiếm nâng cao để lọc truyện theo chủ đề

Điểm mạnh của thiết kế này là cho phép một truyện thuộc nhiều thể loại cùng lúc, tạo độ linh hoạt và hỗ trợ bộ lọc hiệu quả hơn.

### 5. Module Tài Khoản & Xác Thực

Module tài khoản đảm bảo người dùng có thể đăng ký, xác thực và bảo mật thông tin cá nhân.

Chức năng chính:

- Đăng ký tài khoản với username, email, password, họ tên
- Kiểm tra trùng username/email trước khi lưu dữ liệu
- Xác thực đăng nhập bằng Spring Security
- Mã hóa mật khẩu trước khi lưu vào database
- Chặn hoặc khóa tài khoản khi cần thiết
- Chỉnh sửa thông tin cá nhân, bao gồm họ tên, email và avatar
- Hỗ trợ avatar mặc định và upload ảnh cá nhân mới

Quy trình xác thực và phân quyền được triển khai bằng Spring Security, giúp hệ thống bảo mật tốt hơn và tránh truy cập trái phép vào các tuyến admin.

### 6. Module Vai Trò & Quyền Hạn

Module này là phần cốt lõi của cơ chế phân quyền trong hệ thống.

Chức năng chính:

- Khởi tạo và quản lý vai trò như USER, ADMIN
- Gán quyền hạn cho từng vai trò
- Liên kết tài khoản với nhiều vai trò nếu cần
- Kiểm soát quyền truy cập theo module như truyện, chương, tài khoản, thể loại, vai trò, đánh giá
- Cấm hoặc cho phép truy cập các URL tùy theo quyền đã được xác định

Ví dụ: người có quyền XEM_TRUYEN chỉ có thể xem dữ liệu truyện; người có quyền THEM_CHUONG mới có thể thêm chương. Cách thiết kế này giúp hệ thống linh hoạt và có thể mở rộng khi có thêm chức năng mới.

### 7. Module Đánh Giá & Bình Luận

Module này tạo tính tương tác giữa người đọc và hệ thống, giúp nâng cao trải nghiệm cộng đồng.

Chức năng chính:

- Người dùng đánh giá truyện theo mức sao
- Tính toán điểm trung bình dựa trên tổng điểm và số lượt đánh giá
- Hiển thị số sao đã chọn trước đó khi người dùng xem lại truyện
- Người dùng có thể bình luận trực tiếp trên trang chi tiết truyện
- Giới hạn nội dung bình luận trống hoặc không hợp lệ
- Admin có thể xem tổng quan và quản lý bình luận/thống kê đánh giá của từng truyện

Mục tiêu của module này là tăng mức độ tương tác và tạo ra cảm giác “sống” cho nền tảng truyện.

### 8. Module Truyện Yêu Thích

Module này giúp người dùng lưu lại các bộ truyện mình thích để xem lại sau.

Chức năng chính:

- Thêm truyện vào danh sách yêu thích
- Xóa truyện khỏi danh sách yêu thích
- Hiển thị danh sách đã lưu trong profile hoặc trang riêng
- Hỗ trợ các trải nghiệm cá nhân hóa, chẳng hạn như người dùng quay lại với nội dung mình quan tâm

Module này tăng tính gắn kết với người dùng và là một yếu tố quan trọng trong các website nội dung tương tác.

### 9. Module Quản Trị Dashboard & Thống Kê

Module này dành cho admin, giúp tổng quan tình trạng hoạt động của hệ thống trong thời gian thực.

Chức năng chính:

- Hiển thị số lượng tổng truyện, chương, tài khoản, bình luận, đánh giá
- Thống kê top truyện hot nhất theo lượt xem
- Biểu đồ cột biểu diễn lượt xem trên các truyện nổi bật
- Biểu đồ tròn hiển thị phân bố số lượng truyện theo thể loại
- Liệt kê bình luận mới nhất và các hoạt động tương tác gần đây

Dashboard không chỉ mang tính trực quan mà còn giúp admin nhanh chóng nhận diện xu hướng nội dung và phát hiện các vấn đề về dữ liệu hoặc sự tương tác người dùng.

### 10. Module Quản Lý Tài Khoản Admin

Module này cho phép admin quản trị toàn bộ tài khoản trong hệ thống.

Chức năng chính:

- Xem danh sách tài khoản theo phân trang
- Tìm kiếm tài khoản theo username hoặc thông tin liên quan
- Cập nhật thông tin tài khoản
- Gán hoặc đổi vai trò cho tài khoản
- Khóa/mở khóa tài khoản
- Xóa tài khoản nếu cần

Module này rất quan trọng trong việc kiểm soát quyền truy cập và đảm bảo hệ thống không bị lộ dữ liệu hoặc sử dụng sai mục đích.

### 11. Module Quản Lý Vai Trò & Quyền Theo Cấp Độ

Module này mở rộng thêm khả năng điều hành hệ thống theo cấu trúc phân quyền rõ ràng.

Chức năng chính:

- Tạo mới vai trò với tên và mô tả
- Chỉnh sửa vai trò hiện có
- Gán hoặc bỏ quyền cho từng vai trò
- Xóa vai trò nếu không còn cần thiết
- Theo dõi và kiểm soát quyền truy cập trên từng module chức năng

Cách hoạt động này phù hợp với các hệ thống có nhiều nhóm người dùng và cần phân tầng chức năng rõ ràng, ví dụ: người quản trị, biên tập viên, người dùng thường.

## Luồng dữ liệu và business logic

Dự án sử dụng cơ sở dữ liệu MySQL với các bảng chính như:

- truyen
- chuong
- the_loai
- tai_khoan
- vai_tro
- quyen
- danh_gia
- binh_luan
- truyen_yeu_thich

Các quan hệ được mô hình hóa bằng JPA:

- Truyen - N - N TheLoai
- Truyen - 1 - N Chuong
- TaiKhoan - N - N VaiTro
- VaiTro - N - N Quyen
- Truyen - 1 - N DanhGia
- Truyen - 1 - N BinhLuan

Điều này giúp hệ thống dễ mở rộng, đồng thời hỗ trợ phân quyền mạnh mẽ và quản lý dữ liệu truyện hiệu quả.

## Cấu hình chạy ứng dụng

### Yêu cầu

- Java 21+
- Maven 3.8+
- MySQL 8+

### Bước 1: Tạo database

Tạo một database rỗng tên `webtruyen` trong MySQL.

```sql
CREATE DATABASE webtruyen;
```

### Bước 2: Cấu hình kết nối database

Mở file `src/main/resources/application.properties` và cập nhật username/password MySQL của bạn:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/webtruyen?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
server.servlet.context-path=/webtruyen
```

### Bước 3: Chạy ứng dụng

Trên Windows:

```bash
mvnw.cmd spring-boot:run
```

Trên Linux/macOS:

```bash
./mvnw spring-boot:run
```

Hoặc chạy class main:

```bash
mvn spring-boot:run
```

### Bước 4: Truy cập ứng dụng

Sau khi chạy thành công, mở trình duyệt:

```text
http://localhost:8080/webtruyen
```

## Đường dẫn quan trọng

- Trang chủ: `/webtruyen/`
- Trang đăng nhập: `/webtruyen/dang-nhap`
- Trang đăng ký: `/webtruyen/dang-ky`
- Chi tiết truyện: `/webtruyen/chi-tiet-truyen/{id}`
- Admin dashboard: `/webtruyen/admin`

## Lưu ý khi phát triển

- Project có cấu hình `spring.jpa.hibernate.ddl-auto=update`, nên Hibernate sẽ tự tạo/cập nhật schema cơ sở dữ liệu nếu cần.
- Dự án cũng đã cấu hình upload hình ảnh và tài nguyên tĩnh, phục vụ ảnh bìa truyện, avatar người dùng và hình ảnh chương truyện.
- Bảo mật đang được xử lý bằng Spring Security và custom `PasswordEncoder` theo SHA-256.
