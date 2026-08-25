package com.flogin.webtruyen.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.service.TruyenService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.*;


@Controller
@RequestMapping("/admin/quan-ly-truyen")
public class AdminTruyenController {

    @Autowired
    TruyenService busTruyen;

    @GetMapping({"", "/"})
    public String loadDanhSachTruyen(@RequestParam(name="page", defaultValue = "1") int page, Model model) {
        
        int tongSoSanPham = 2;
        Page<Truyen> pageAble = busTruyen.layDanhSachTheoPhanTrang(page, tongSoSanPham);

        model.addAttribute("danhSachTruyen", pageAble.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageAble.getTotalPages());

        return "admin/truyen_admin";
    }

    @GetMapping("/tim-kiem")
    public String timKiemCoBan(@RequestParam(name="page", defaultValue = "1") int page,@RequestParam("search") String tuKhoa, Model model) {
        int tongSoSanPham = 2;
        Page<Truyen> pageAble = busTruyen.timKiemCoBan(page, tongSoSanPham, tuKhoa);

        model.addAttribute("danhSachTruyen", pageAble.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageAble.getTotalPages());

        model.addAttribute("tuKhoa", tuKhoa);

        return "admin/truyen_admin";
    }
    

    @PostMapping("/them-truyen")
    public String xuLyThemTruyen(@ModelAttribute Truyen truyen, @RequestParam("fileAnh") MultipartFile file, RedirectAttributes redirectAttributes) {

        if(truyen != null) {
            try {
                if(file != null && !file.isEmpty()) {
                    String tenFile = file.getOriginalFilename();
                    Path duongDan = Paths.get("src/main/resources/static/images/truyen/" + tenFile);
                    Files.copy(file.getInputStream(), duongDan, StandardCopyOption.REPLACE_EXISTING);
                    truyen.setAnhBia(tenFile);
                } else {
                    truyen.setAnhBia("default.jpg");
                }
                
                truyen.setLuotXem(0);
                
                busTruyen.themTruyen(truyen);
                redirectAttributes.addFlashAttribute("thongbao", "Đã thêm thành công truyện mới");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/admin/quan-ly-truyen";
    }

    @GetMapping("/xoa-truyen/{id}")
    public String xuLyXoaTruyen(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Truyen truyen = busTruyen.layThongTinTruyen(id);
            if(truyen != null) {
                busTruyen.xoaTruyen(truyen);
                redirectAttributes.addFlashAttribute("thongbao", "Đã xóa thành công truyện có ID: " + id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("loi", "Không thể xóa! Chân kinh này đang chứa các chương bên trong.");
        }
        return "redirect:/admin/quan-ly-truyen";
    }
    
    @PostMapping("/sua-truyen")
    public String xuLySuaTruyen(@ModelAttribute Truyen truyen, @RequestParam("fileAnh") MultipartFile file, RedirectAttributes redirectAttributes) {
        if(truyen != null) {
            try {
                Truyen truyenCu = busTruyen.layThongTinTruyen(truyen.getId());
                
                if(file != null && !file.isEmpty()) {
                    String tenFile = file.getOriginalFilename();
                    Path duongDan = Paths.get("src/main/resources/static/images/truyen/" + tenFile);
                    Files.copy(file.getInputStream(), duongDan, StandardCopyOption.REPLACE_EXISTING);
                    truyen.setAnhBia(tenFile);
                } else 
                    {
                    truyen.setAnhBia(truyenCu.getAnhBia());
                }
                
                truyen.setLuotXem(truyenCu.getLuotXem()); 
                
                busTruyen.suaTruyen(truyen);
                redirectAttributes.addFlashAttribute("thongbao", "Đã cập nhật thành công truyện với ID : " + truyen.getId());
                
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        return "redirect:/admin/quan-ly-truyen";
    }
}