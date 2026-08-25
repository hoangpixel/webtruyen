package com.flogin.webtruyen.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        
        // Kiểm tra xem có thẻ adminLog không
        if (session == null || session.getAttribute("adminLog") == null) {
            // Không có thẻ -> Đá văng ra cổng đăng nhập của Admin
            // request.getContextPath() chính là cái chữ /webtruyen của ông á
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false; 
        }
        
        return true; // Có thẻ thì mời sếp vào Sở Chỉ Huy
    }
}