package com.flogin.webtruyen.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.security.MessageDigest;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // 1. ÉP SPRING SECURITY XÀI ĐÚNG MÃ HÓA SHA-256
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(rawPassword.toString().getBytes("UTF-8"));
                    return Base64.getEncoder().encodeToString(hash);
                } catch (Exception ex) {
                    throw new RuntimeException("Lỗi mã hóa mật khẩu", ex);
                }
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // Kiểm tra xem pass người dùng gõ vào sau khi mã hóa có khớp với pass dưới DB không
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 2. BỘ ĐIỀU HƯỚNG THÔNG MINH (ADMIN vào Sở chỉ huy, USER ra Trang chủ)
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            String contextPath = request.getContextPath();

            if (isAdmin) {
                response.sendRedirect(contextPath + "/admin");
            } else {
                response.sendRedirect(contextPath + "/");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. CÁC ĐƯỜNG DẪN THẢ CỬA (Không cần đăng nhập)
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/", "/trang-chu").permitAll()
                .requestMatchers("/dang-nhap", "/xu-ly-dang-nhap", "/dang-ky", "/xu-ly-dang-ky", "/admin/login").permitAll()
                
                // ================= MODULE TRUYỆN =================
                .requestMatchers(HttpMethod.GET, "/admin/quan-ly-truyen", "/admin/quan-ly-truyen/**").hasAuthority("XEM_TRUYEN")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-truyen/them-truyen").hasAuthority("THEM_TRUYEN")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-truyen/sua-truyen").hasAuthority("SUA_TRUYEN")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-truyen/xoa-truyen").hasAuthority("XOA_TRUYEN")

                // ================= MODULE CHƯƠNG =================
                .requestMatchers(HttpMethod.GET, "/admin/quan-ly-chuong", "/admin/quan-ly-chuong/**").hasAuthority("XEM_CHUONG")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-chuong/them-chuong").hasAuthority("THEM_CHUONG")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-chuong/sua-chuong").hasAuthority("SUA_CHUONG")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-chuong/xoa-chuong").hasAuthority("XOA_CHUONG")

                // ================= MODULE THỂ LOẠI =================
                .requestMatchers(HttpMethod.GET, "/admin/quan-ly-the-loai", "/admin/quan-ly-the-loai/**").hasAuthority("XEM_THE_LOAI")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-the-loai/them").hasAuthority("THEM_THE_LOAI")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-the-loai/sua").hasAuthority("SUA_THE_LOAI")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-the-loai/xoa").hasAuthority("XOA_THE_LOAI")

                // ================= MODULE TÀI KHOẢN =================
                .requestMatchers(HttpMethod.GET, "/admin/quan-ly-tai-khoan", "/admin/quan-ly-tai-khoan/**").hasAuthority("XEM_TAI_KHOAN")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-tai-khoan/khoa", "/admin/quan-ly-tai-khoan/mo-khoa").hasAuthority("KHOA_TAI_KHOAN")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-tai-khoan/phan-quyen").hasAuthority("PHAN_QUYEN_TAI_KHOAN")

                // ================= MODULE ĐÁNH GIÁ =================
                .requestMatchers(HttpMethod.GET, "/admin/quan-ly-danh-gia", "/admin/quan-ly-danh-gia/**").hasAuthority("XEM_DANH_GIA")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-danh-gia/xoa").hasAuthority("XOA_DANH_GIA")

                // ================= MODULE VAI TRÒ =================
                .requestMatchers(HttpMethod.GET, "/admin/quan-ly-vai-tro", "/admin/quan-ly-vai-tro/**").hasAuthority("XEM_VAI_TRO")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-vai-tro/them").hasAuthority("THEM_VAI_TRO")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-vai-tro/sua").hasAuthority("SUA_VAI_TRO")
                .requestMatchers(HttpMethod.POST, "/admin/quan-ly-vai-tro/xoa").hasAuthority("XOA_VAI_TRO")
                
                // 2. CHỐT CHẶN CUỐI CÙNG CHO KHU VỰC ADMIN (Bắt buộc phải nằm ở đây)
                // Ý nghĩa: Các link /admin còn lại (ví dụ: Trang Tổng quan Dashboard) chỉ cần Đăng nhập là vào được.
                .requestMatchers("/admin/**").authenticated() 
                
                // 3. CÁC LINK CÒN LẠI NGOÀI TRANG CHỦ (Tự do qua lại)
                .anyRequest().permitAll() 
            )
            .formLogin(form -> form
                .loginPage("/dang-nhap") 
                .loginProcessingUrl("/xu-ly-dang-nhap") 
                .successHandler(customSuccessHandler())
                .failureUrl("/dang-nhap?error=true") 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/dang-xuat")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}