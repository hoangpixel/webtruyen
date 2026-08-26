package com.flogin.webtruyen.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.security.CustomUserDetails;
import com.flogin.webtruyen.service.TaiKhoanService;

import org.springframework.ui.Model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
public class UserController {
    
    @Autowired
    TaiKhoanService busTaiKhoan;

    @GetMapping("/chi-tiet-nguoi-dung")
    private String hienThiChiTietUser(Model model, Authentication auth)
    {
        String username = auth.getName();
        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(username);
        model.addAttribute("tkNguoiDung", tk);
        return "user/chi_tiet_user";
    }

    @PostMapping("/xu-ly-cap-nhat-thong-tin")
    public String getMethodName(@RequestParam int id,@RequestParam String hoTen, @RequestParam String email, @RequestParam("matKhauMoi") String password, @RequestParam("fileAvatar") MultipartFile file, Authentication auth, Model model, RedirectAttributes ra) {
        String username = auth.getName();
        
        if(busTaiKhoan.kiemTraTrungEmailVoiIdKhac(email,id))
        {
            TaiKhoan tkNguoiDung = busTaiKhoan.layThongTinTaiKhoan(username);
            ra.addFlashAttribute("loi", "Email đã tồn tại!");
            model.addAttribute("tkNguoiDung", tkNguoiDung);
            return "redirect:/chi-tiet-nguoi-dung";
        }

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
                CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                String tenAnhMoi = userDetails.getTaiKhoan().getAvatar();
                if(file != null && !file.isEmpty())
                {
                    String tenFile = file.getOriginalFilename();
                    Path duongDan = Paths.get("src/main/resources/static/images/avatar/" + tenFile);
                    Files.copy(file.getInputStream(), duongDan, StandardCopyOption.REPLACE_EXISTING);
                    tk.setAvatar(tenFile);
                    tenAnhMoi = tenFile;
                }
                if(busTaiKhoan.capNhatTaiKhoan(tk))
                {
                    userDetails.getTaiKhoan().setAvatar(tenAnhMoi);
                    userDetails.getTaiKhoan().setHoTen(hoTen);
                    ra.addFlashAttribute("thongbao", "Cập nhật thông tin thành công!");
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return "redirect:/chi-tiet-nguoi-dung";
    }
    
}
