package com.flogin.webtruyen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor; // Bảo vệ Admin

    @Autowired
    private AuthInterceptor authInterceptor; // Bảo vệ User

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns("/admin/**") 
                .excludePathPatterns("/admin/login", "/admin/logout"); 

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") 
                .excludePathPatterns(
                        "/css/**", "/js/**", "/images/**", "/webjars/**",
                        "/", "/tim-kiem", "/chi-tiet-truyen/**", "/doc-truyen/**",
                        "/login-user", "/user/login", "/dang-ky", "/xu-ly-dang-ky", "/logout",
                        "/admin/**",
                        "/error"
                );
    }
}