package com.flogin.webtruyen.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.service.TaiKhoanService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    TaiKhoanService busTaiKhoan;

    @GetMapping("/login")
    public String loadTrangLoginAdmin() {
        return "admin/login_admin";
    }
    
    @PostMapping("/login")
    public String xuLyDangNhapAdmin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
       if(busTaiKhoan.kiemTraTaiKhoan(username, password))
       {
            TaiKhoan tk = busTaiKhoan.layTaiKhoan(username, password);
            if(tk.hasRole("ADMIN"))
            {
                session.setAttribute("adminLog", tk.getUsername());
                session.setAttribute("adminAvatar", tk.getAvatar());
                session.setAttribute("adminVaiTro", "ADMIN");
                return "redirect:/admin";
            }else
            {
                model.addAttribute("loi", "Cảnh báo : Tài khoản của bạn không có quyền truy cập vào đây");
                return "admin/login_admin";
            }
       }
       model.addAttribute("loi", "Sai tài khoản hoặc mật khẩu, liên hệ ADMIN để lấy lại tài khoản");
        return "admin/login_admin";
    }
    

    @GetMapping("/logout")
    public String dangXuatAdmin(HttpSession session) {
        session.invalidate();
        session.removeAttribute("adminLog");
        session.removeAttribute("adminAvatar");
        session.removeAttribute("adminVaiTro");
        return "redirect:/admin/login";
    }
    
}
