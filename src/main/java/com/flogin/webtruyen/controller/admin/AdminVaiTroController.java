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
import com.flogin.webtruyen.repository.QuyenRepository;
import com.flogin.webtruyen.repository.VaiTroRepository;
import com.flogin.webtruyen.service.TaiKhoanService;
import com.flogin.webtruyen.service.VaiTroService;

@Controller
@RequestMapping("/admin/quan-ly-vai-tro")
public class AdminVaiTroController {

    @Autowired 
    VaiTroService busVaiTro;
    
    @Autowired 
    QuyenRepository repoQuyen; // Móc quyền lên cho Checkbox

    @GetMapping({"", "/"})
    public String loadDanhSach(Model model) {
        model.addAttribute("danhSachVaiTro", busVaiTro.layDanhSach());
        // Lấy hết các Quyền (Thêm, Sửa, Xóa...) quăng ra HTML
        model.addAttribute("danhSachTatCaQuyen", repoQuyen.findAll()); 
        return "admin/vai_tro_admin";
    }

    @PostMapping("/them-vai-tro")
    public String themVaiTro(@ModelAttribute VaiTro vtInput, 
                             @RequestParam(name = "quyenIds", required = false) List<Integer> quyenIds, 
                             RedirectAttributes ra) {
        if (busVaiTro.kiemTraTrungTen(vtInput.getTenVaiTro())) {
            ra.addFlashAttribute("loi", "Vai trò này đã tồn tại!");
            return "redirect:/admin/quan-ly-vai-tro";
        }

        // Nếu admin có tích chọn quyền, bốc các quyền đó từ DB lên rồi nhét vào Vai trò
        if (quyenIds != null && !quyenIds.isEmpty()) {
            vtInput.setDanhSachQuyen(repoQuyen.findAllById(quyenIds));
        }
        
        busVaiTro.luuVaiTro(vtInput);
        ra.addFlashAttribute("thongbao", "Thêm thành công vai trò: " + vtInput.getTenVaiTro());
        return "redirect:/admin/quan-ly-vai-tro";
    }

    @PostMapping("/sua-vai-tro")
    public String suaVaiTro(@ModelAttribute VaiTro vtInput, 
                            @RequestParam(name = "quyenIds", required = false) List<Integer> quyenIds, 
                            RedirectAttributes ra) {
        
        if (busVaiTro.kiemTraTrungLucSua(vtInput.getTenVaiTro(), vtInput.getId())) {
            ra.addFlashAttribute("loi", "Tên vai trò bị trùng với một vai trò khác!");
            return "redirect:/admin/quan-ly-vai-tro";
        }

        VaiTro vtCu = busVaiTro.layThongTin(vtInput.getId());
        if (vtCu != null) {
            vtCu.setTenVaiTro(vtInput.getTenVaiTro());
            vtCu.setMoTa(vtInput.getMoTa());
            
            if (quyenIds != null && !quyenIds.isEmpty()) {
                vtCu.setDanhSachQuyen(repoQuyen.findAllById(quyenIds));
            } else {
                vtCu.setDanhSachQuyen(new ArrayList<>()); // Trắng tay
            }
            
            busVaiTro.luuVaiTro(vtCu);
            ra.addFlashAttribute("thongbao", "Đã cập nhật vai trò: " + vtCu.getTenVaiTro());
        }
        return "redirect:/admin/quan-ly-vai-tro";
    }

    @GetMapping("/xoa-vai-tro/{id}")
    public String xoaVaiTro(@PathVariable("id") int id, RedirectAttributes ra) {
        VaiTro vt = busVaiTro.layThongTin(id);
        if (vt != null) {
            try {
                busVaiTro.xoaVaiTro(vt);
                ra.addFlashAttribute("thongbao", "Đã xóa vai trò: " + vt.getTenVaiTro());
            } catch (Exception e) {
                ra.addFlashAttribute("loi", "Không thể xóa! Vai trò này đang được cấp cho ai đó.");
            }
        }
        return "redirect:/admin/quan-ly-vai-tro";
    }
}