package com.flogin.webtruyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.VaiTro;
import com.flogin.webtruyen.repository.VaiTroRepository;
import com.flogin.webtruyen.service.TaiKhoanService;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.LockedException;



@Controller
public class UserLoginController {
    @Autowired
    TaiKhoanService bus;

    @Autowired
    VaiTroRepository repoVaiTro;

    @GetMapping("/dang-nhap")
    public String login(@RequestParam(value = "error", required = false) String error, 
                        HttpServletRequest request, 
                        Model model) {
                        
        if (error != null) {
            String thongBaoLoi = "Sai tài khoản hoặc mật khẩu! Vui lòng thử lại."; 

            HttpSession session = request.getSession(false);
            if (session != null) {
                AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (ex != null) {
                    // Gom chung 2 thằng Locked và Disabled vào cùng 1 câu chửi
                    if (ex instanceof DisabledException || ex instanceof LockedException) {
                        thongBaoLoi = "Tài khoản của bạn đã bị khóa! Liên hệ ADMIN để mở khóa.";
                    } else if (ex instanceof BadCredentialsException) {
                        thongBaoLoi = "Sai tài khoản hoặc mật khẩu! Vui lòng thử lại.";
                    }
                }
            }
            model.addAttribute("loi", thongBaoLoi);
        }
        return "user/login_user";
    }
    

    // @PostMapping("/user/login")
    // public String kiemTraDangNhap(@RequestParam String username,@RequestParam String password, Model model, HttpSession session) {
        
    //     if(bus.kiemTraTaiKhoan(username, password))
    //     {
    //         TaiKhoan tk = bus.layTaiKhoan(username, password);
    //         session.setAttribute("nguoiDung", tk.getUsername());
    //         session.setAttribute("vaiTro", tk.getChucVuString());
    //         session.setAttribute("avatar", tk.getAvatar());
    //         return "redirect:/";
    //     }
    //     model.addAttribute("loi", "Sai tài khoản hoặc mật khẩu!");
    //     return "/user/login_user";
    // }
    
    // @GetMapping("/logout")
    // public String logout(HttpSession session) {
    //     session.invalidate();
    //     return "redirect:/";
    // }
    
    @GetMapping("/dang-ky")
    public String moTrangDangKy()
    {
        return "user/dang_ky";
    }

    @PostMapping("/xu-ly-dang-ky")
    public String dangKyTaiKhoan(@RequestParam String username, @RequestParam String password, @RequestParam String hoTen, @RequestParam String email,@RequestParam String confirmPassword ,Model model) {
        if(bus.kiemTraTrungEmail(email))
        {
            model.addAttribute("loi", "Email đã tồn tại!");
            return "user/dang_ky";
        }
        
        if(!username.matches("^[a-zA-Z0-9_]+$")) 
        {
            model.addAttribute("loi", "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới (_), không được có dấu hoặc khoảng trắng!");
            return "user/dang_ky";
        }

        if(bus.kiemTraTrungUsername(username)) 
        {
            model.addAttribute("loi", "Tên đăng nhập đã tồn tại!");
            return "user/dang_ky";
        }
        
        if(!bus.kiemTraTrungPassword(password, confirmPassword)) 
        {
            model.addAttribute("loi", "Mật khẩu không khớp nhau!");
            return "user/dang_ky";
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setHoTen(hoTen);
        tk.setEmail(email);
        tk.setUsername(username);
        tk.setPassword(password);

        VaiTro roleUser = repoVaiTro.findByTenVaiTro("USER");
        if(roleUser != null) 
        {
            tk.setDanhSachVaiTro(java.util.List.of(roleUser)); 
        }

        bus.themTaiKhoan(tk);
        
        return "redirect:/dang-nhap";
    }
}
