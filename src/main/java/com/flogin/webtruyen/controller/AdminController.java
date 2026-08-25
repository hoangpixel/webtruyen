package com.flogin.webtruyen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"", "/"}) 
    public String loadTrangTongQuan(HttpSession session) 
    {
        
        if (session.getAttribute("adminLog") == null) 
            {
            return "redirect:/admin/login";
        }
        
        return "admin/admin_index"; 
    }
}