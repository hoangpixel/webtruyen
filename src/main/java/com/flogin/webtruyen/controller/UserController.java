package com.flogin.webtruyen.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.service.TaiKhoanService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardCopyOption;

@Controller
public class UserController {
    
    @Autowired
    TaiKhoanService busTaiKhoan;

    @GetMapping("/chi-tiet-user")
    private String hienThiChiTietUser(Model model, HttpSession session)
    {
        String username = (String) session.getAttribute("nguoiDung");
        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(username);
        model.addAttribute("tkNguoiDung", tk);
        return "user/chi_tiet_user";
    }

    @PostMapping("/xu-ly-cap-nhat-thong-tin")
    public String getMethodName(@RequestParam int id,@RequestParam String hoTen, @RequestParam String email, @RequestParam("matKhauMoi") String password, @RequestParam("fileAvatar") MultipartFile file, HttpSession session) {
        String username = (String) session.getAttribute("nguoiDung");
        
        TaiKhoan tk = new TaiKhoan();
        tk.setId(id);
        tk.setHoTen(hoTen);
        tk.setEmail(email);
        tk.setPassword(password);
        tk.setUsername(username);
        
        if(tk != null)
        {
            try
            {
                String tenAnhMoi = (String) session.getAttribute("avatar");
                if(!file.isEmpty() && file != null)
                {
                    String tenFile = file.getOriginalFilename();
                    Path duongDan = Paths.get("src/main/resources/static/images/avatar/" + tenFile);
                    Files.copy(file.getInputStream(), duongDan, StandardCopyOption.REPLACE_EXISTING);
                    tk.setAvatar(tenFile);
                    tenAnhMoi = tenFile;
                }
                if(busTaiKhoan.capNhatTaiKhoan(tk))
                {
                    session.setAttribute("avatar", tenAnhMoi);
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }
    
}
