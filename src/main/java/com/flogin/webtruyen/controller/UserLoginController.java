package com.flogin.webtruyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.service.TaiKhoanService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class UserLoginController {
    @Autowired
    TaiKhoanService bus;

    @GetMapping("/login-user")
    public String login() {
        return "user/login_user";
    }
    

    @PostMapping("/user/login")
    public String kiemTraDangNhap(@RequestParam String username,@RequestParam String password, Model model, HttpSession session) {
        
        if(bus.kiemTraTaiKhoan(username, password))
        {
            TaiKhoan tk = bus.layTaiKhoan(username, password);
            session.setAttribute("nguoiDung", tk.getUsername());
            session.setAttribute("vaiTro", tk.getVaiTro());
            session.setAttribute("avatar", tk.getAvatar());
            return "redirect:/";
        }
        model.addAttribute("loi", "Sai tài khoản hoặc mật khẩu!");
        return "/user/login_user";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/dang-ky")
    public String moTrangDangKy()
    {
        return "user/dang_ky";
    }

    @PostMapping("/xu-ly-dang-ky")
    public String dangKyTaiKhoan(@RequestParam String username, @RequestParam String password, @RequestParam String hoTen, @RequestParam String email,@RequestParam String confirmPassword ,Model model) {
        if(bus.kiemTraTrungUsername(username)) 
        {
            model.addAttribute("loiDangKy", "Tên đăng nhập đã tồn tại!");
            return "user/dang_ky";
        }
        
        if(bus.kiemTraTrungEmail(email))
        {
            model.addAttribute("loiDangKy", "Email đã tồn tại!");
            return "user/dang_ky";
        }
        
        if(!bus.kiemTraTrungPassword(password, confirmPassword)) 
        {
            model.addAttribute("loiDangKy", "Mật khẩu không khớp nhau!");
            return "user/dang_ky";
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setHoTen(hoTen);
        tk.setEmail(email);
        tk.setUsername(username);
        tk.setPassword(password);
        tk.setVaiTro("user");

        bus.themTaiKhoan(tk);
        
        return "redirect:/login-user";
    }
}
