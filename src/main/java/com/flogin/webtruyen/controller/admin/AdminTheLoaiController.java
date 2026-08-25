package com.flogin.webtruyen.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flogin.webtruyen.model.TheLoai;
import com.flogin.webtruyen.service.TheLoaiService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin/quan-ly-the-loai")
public class AdminTheLoaiController {
    @Autowired
    TheLoaiService bus;

    @GetMapping({"", "/"})
    public String loadDanhSachTheLoai(@RequestParam(name ="page", defaultValue = "1") int page, Model model) {
        int tongSoTL = 10;
        Page<TheLoai> pageTheLoai = bus.layDanhSachTheoPhanTrang(page, tongSoTL);

        model.addAttribute("danhSachTheLoai", pageTheLoai.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageTheLoai.getTotalPages());

        return "admin/the_loai_admin";
    }

    @GetMapping("/tim-kiem")
    public String timKiemCoBan(@RequestParam(name="page", defaultValue = "1") int page, @RequestParam("search") String tuKhoa, Model model) {
        int tongSoTL = 10;
        Page<TheLoai> pageTheLoai =  bus.timKiemCoBan(page, tongSoTL, tuKhoa);

        model.addAttribute("danhSachTheLoai", pageTheLoai.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageTheLoai.getTotalPages());
        model.addAttribute("tuKhoa", tuKhoa);

        return "admin/the_loai_admin";
    }

    @PostMapping("/them-the-loai")
    public String xuLyThemTheLoai(@ModelAttribute TheLoai theloai, RedirectAttributes redirectAttributes) {
        if(theloai != null) {
            // Check trùng lúc Thêm
            if(bus.kiemTraTrungTenTheLoai(theloai.getTenTheLoai())) 
            {
                redirectAttributes.addFlashAttribute("loi", "Thất bại: Tên thể loại '" + theloai.getTenTheLoai() + "' đã tồn tại!");
                return "redirect:/admin/quan-ly-the-loai";
            }

            if(bus.themTheLoai(theloai))
            {
                redirectAttributes.addFlashAttribute("thongbao", "Tuyệt vời! Đã thêm thể loại: " + theloai.getTenTheLoai());
            } else {
                redirectAttributes.addFlashAttribute("loi", "Có lỗi xảy ra khi thêm thể loại!");
            }
        }
        return "redirect:/admin/quan-ly-the-loai";
    }
    
    @PostMapping("/sua-the-loai")
    public String xuLySuaTheLoai(@ModelAttribute TheLoai theloai, RedirectAttributes redirectAttributes) {
        if(theloai != null) {
            if(bus.kiemTraTrungTenLucSua(theloai.getTenTheLoai(), theloai.getId())) 
            {
                redirectAttributes.addFlashAttribute("loi", "Thất bại: Tên thể loại '" + theloai.getTenTheLoai() + "' đã bị trùng với một thể loại khác!");
                return "redirect:/admin/quan-ly-the-loai";
            }

            if(bus.suaTheLoai(theloai)) 
            {
                redirectAttributes.addFlashAttribute("thongbao", "Cập nhật thành công thể loại: " + theloai.getTenTheLoai());
            } else {
                redirectAttributes.addFlashAttribute("loi", "Cập nhật thất bại thể loại: " + theloai.getTenTheLoai());
            }
        }
        return "redirect:/admin/quan-ly-the-loai";
    }
    
    @GetMapping("/xoa-the-loai/{id}")
    public String xuLyXoaTheLoai(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        TheLoai tl = bus.layThongTinTheLoai(id);
        if(tl != null) {
            if(bus.xoaTheLoai(tl)) 
            {
                redirectAttributes.addFlashAttribute("thongbao", "Đã xóa sổ thể loại: " + tl.getTenTheLoai());
            } else {
                redirectAttributes.addFlashAttribute("loi", "Xóa thất bại thể loại: " + tl.getTenTheLoai());
            }
        } else {
            redirectAttributes.addFlashAttribute("loi", "Không tìm thấy thể loại này!");
        }
        return "redirect:/admin/quan-ly-the-loai";
    }
}