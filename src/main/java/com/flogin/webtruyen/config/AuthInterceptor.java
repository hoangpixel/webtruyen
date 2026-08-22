package com.flogin.webtruyen.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component // Khai báo cho Spring biết đây là một bộ phận của hệ thống
public class AuthInterceptor implements HandlerInterceptor {

    // Hàm preHandle này sẽ chạy TRƯỚC KHI request chạm tới Controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // Xin xem cái thẻ (Session)
        HttpSession session = request.getSession();
        
        // Nếu không có thẻ hoặc thẻ chưa có tên người dùng -> Lạ mặt!
        if (session == null || session.getAttribute("nguoiDung") == null) {
            // Đuổi cổ về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // Đóng barie chặn lại, không cho chạy tiếp
        }
        
        // Hợp lệ thì mở barie cho đi tiếp vào Controller
        return true;
    }
}