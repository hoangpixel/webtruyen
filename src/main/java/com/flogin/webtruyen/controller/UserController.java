package com.flogin.webtruyen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    
// Khi khách hàng gõ "localhost:8080/index" hoặc "localhost:8080/" trên trình duyệt
    @GetMapping({"/index", "/"})
    public String hienThiTrangChu() {
        
        // Trả về đúng TÊN FILE của trang HTML (không cần đuôi .html)
        // Spring Boot sẽ tự động chui vào thư mục templates tìm file index.html
        return "index"; 
    }
}
