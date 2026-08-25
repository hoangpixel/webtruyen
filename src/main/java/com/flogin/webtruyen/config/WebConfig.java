package com.flogin.webtruyen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns("/admin/**") 
                // Thêm ngoại lệ cho css, js của admin nếu có
                .excludePathPatterns(
                        "/admin/login", 
                        "/admin/logout",
                        "/admin/css/**", 
                        "/admin/js/**", 
                        "/admin/img/**"
                );

registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") 
                .excludePathPatterns(
                        // Thêm /user/js/**, /user/css/** vào để thả cửa cho file tĩnh của User
                        "/css/**", "/js/**", "/images/**", "/webjars/**",
                        "/user/js/**", "/user/css/**", "/user/img/**", // <--- THÊM KHÚC NÀY NÈ
                        
                        // Các trang không cần đăng nhập
                        "/", "/tim-kiem", "/chi-tiet-truyen/**", "/doc-truyen/**",
                        "/login-user", "/user/login", "/dang-ky", "/xu-ly-dang-ky", "/logout",
                        
                        // Né khu vực Admin và lỗi
                        "/admin/**",
                        "/error"
                );
    }
}