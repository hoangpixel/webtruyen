package com.flogin.webtruyen.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.VaiTro;
import com.flogin.webtruyen.repository.VaiTroRepository;
import com.flogin.webtruyen.service.TaiKhoanService;

@Controller
@RequestMapping("/admin/quan-ly-tai-khoan")
public class AdminTaiKhoanController {
    
    @Autowired
    TaiKhoanService busTaiKhoan;
    
    @Autowired
    VaiTroRepository repoVaiTro; 

    @GetMapping({"", "/"})
    public String loadDanhSachTaiKhoan(@RequestParam(name ="page", defaultValue = "1") int page, Model model) {
        int tongSo = 10;
        Page<TaiKhoan> pageTaiKhoan = busTaiKhoan.layDanhSachTheoPhanTrang(page, tongSo);
        
        model.addAttribute("danhSachTaiKhoan", pageTaiKhoan.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageTaiKhoan.getTotalPages());

        model.addAttribute("danhSachTatCaVaiTro", repoVaiTro.findAll());

        return "admin/tai_khoan_admin";
    }

    @GetMapping("/tim-kiem")
    public String timKiemTaiKhoan(@RequestParam(name="page", defaultValue = "1") int page, @RequestParam("search") String tuKhoa, Model model) {
        int tongSo = 10;
        Page<TaiKhoan> pageTaiKhoan = busTaiKhoan.timKiemCoBan(page, tongSo, tuKhoa);

        model.addAttribute("danhSachTaiKhoan", pageTaiKhoan.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageTaiKhoan.getTotalPages());
        model.addAttribute("tuKhoa", tuKhoa);

        model.addAttribute("danhSachTatCaVaiTro", repoVaiTro.findAll());

        return "admin/tai_khoan_admin";
    }

    @PostMapping("/sua-tai-khoan")
    public String xuLySuaTaiKhoan(
            @ModelAttribute TaiKhoan tkInput, 
            @RequestParam(name = "vaiTroIds", required = false) List<Integer> vaiTroIds, 
            RedirectAttributes redirectAttributes) {
        
        TaiKhoan tkCu = busTaiKhoan.layThongTinTaiKhoan(tkInput.getUsername());
        
        if (tkCu != null) 
        {
            tkCu.setHoTen(tkInput.getHoTen());
            tkCu.setEmail(tkInput.getEmail());
            tkCu.setTrangThai(tkInput.getTrangThai());
            
            if (vaiTroIds != null && !vaiTroIds.isEmpty()) 
            {
                List<VaiTro> vaiTroMoi = repoVaiTro.findAllById(vaiTroIds);
                tkCu.setDanhSachVaiTro(vaiTroMoi);
            } else {
                tkCu.setDanhSachVaiTro(new ArrayList<>()); 
            }

            if (busTaiKhoan.capNhatTaiKhoan(tkCu)) 
            {
                redirectAttributes.addFlashAttribute("thongbao", "Thiết lập thành công tài khoản: " + tkCu.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("loi", "Thiết lập thất bại!");
            }
        }
        return "redirect:/admin/quan-ly-tai-khoan";
    }
    
    @PostMapping("/them-tai-khoan")
    public String xuLyThemTaiKhoan(@ModelAttribute TaiKhoan tkInput, RedirectAttributes redirectAttributes) {
        if (tkInput != null) {
            if (busTaiKhoan.kiemTraTrungUsername(tkInput.getUsername())) 
            {
                redirectAttributes.addFlashAttribute("loi", "Thất bại: Tên đăng nhập '" + tkInput.getUsername() + "' đã tồn tại!");
                return "redirect:/admin/quan-ly-tai-khoan";
            }
            if (busTaiKhoan.kiemTraTrungEmail(tkInput.getEmail())) 
            {
                redirectAttributes.addFlashAttribute("loi", "Thất bại: Email '" + tkInput.getEmail() + "' đã được sử dụng!");
                return "redirect:/admin/quan-ly-tai-khoan";
            }

            VaiTro roleUser = repoVaiTro.findByTenVaiTro("USER");
            if (roleUser != null) 
            {
                tkInput.setDanhSachVaiTro(new ArrayList<>(List.of(roleUser)));
            }

            tkInput.setTrangThai(1);

            if (busTaiKhoan.themTaiKhoan(tkInput)) 
            {
                redirectAttributes.addFlashAttribute("thongbao", "Đã cấp tài khoản thành công cho: " + tkInput.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("loi", "Có lỗi xảy ra, thêm tài khoản thất bại!");
            }
        }
        return "redirect:/admin/quan-ly-tai-khoan";
    }

    @GetMapping("/xoa-tai-khoan/{id}")
    public String xuLyXoaTaiKhoan(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoanTheoId(id); 
        
        if (tk != null) 
        {
            if (busTaiKhoan.xoaTaiKhoan(tk)) 
            {
                redirectAttributes.addFlashAttribute("thongbao", "Đã xóa vĩnh viễn tài khoản: " + tk.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("loi", "Xóa thất bại! Tài khoản này có thể đang dính líu đến dữ liệu khác.");
            }
        } else {
            redirectAttributes.addFlashAttribute("loi", "Không tìm thấy tài khoản cần xóa!");
        }
        
        return "redirect:/admin/quan-ly-tai-khoan";
    }
}