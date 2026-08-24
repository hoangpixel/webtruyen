package com.flogin.webtruyen.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.flogin.webtruyen.model.BinhLuan;
import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.service.BinhLuanService;
import com.flogin.webtruyen.service.DanhGiaService;
import com.flogin.webtruyen.service.TaiKhoanService;
import com.flogin.webtruyen.service.TruyenService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class TruyenController {
    @Autowired
    TruyenService bus;

    @Autowired
    TaiKhoanService busTaiKhoan;

    @Autowired
    DanhGiaService busDanhGia;

    @Autowired
    BinhLuanService busBinhLuan;

    @GetMapping({"/index", "/"})
    public String loadDanhSachTruyen(@RequestParam(name = "page", defaultValue = "1") int page ,Model model) {

        int pageSize = 2;
        Page<Truyen> pageAble = bus.layDanhSachTheoPhanTrang(page, pageSize);
        model.addAttribute("danhSachTruyen", pageAble.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageAble.getTotalPages());

        List<Truyen> dsHot = bus.layDanhSachTop10TruyenHot();
        model.addAttribute("danhSachHot", dsHot);
        return "index";
    }

    @GetMapping("/tim-kiem")
    public String timKiemCoban(@RequestParam(name = "page", defaultValue = "1") int page,@RequestParam("tenTruyen") String tenTruyen, Model model) {
        int size = 2;
        Page<Truyen> pageAble = bus.timKiemCoBan(page, size, tenTruyen);

        model.addAttribute("danhSachTruyen", pageAble.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageAble.getTotalPages());

        model.addAttribute("tuKhoa", tenTruyen);
        
        return "index";
    }
    
    @GetMapping({"/chi-tiet-truyen/{id}", "/chi-tiet-truyen/{tenTruyen}/{id}"})
    public String xuLyXemChiTietTruyen(@PathVariable("id") int id, Model model, HttpSession session) {
        Truyen infoTruyen = bus.layThongTinTruyen(id);
        model.addAttribute("infoTruyen", infoTruyen);

        List<BinhLuan> dsBinhLuan = busBinhLuan.layDanhSachTheoTruyen(id);
        int tongSoBinhLuan = busBinhLuan.tongSoLuotBinhLuan(id);
        model.addAttribute("danhSachBinhLuan", dsBinhLuan);
        model.addAttribute("tongBinhLuan", tongSoBinhLuan);

        String user = (String) session.getAttribute("nguoiDung");

        if(user != null)
        {
            TaiKhoan infoTaiKhoan = busTaiKhoan.layThongTinTaiKhoan(user);
            DanhGia dg = busDanhGia.layThongTinDanhGia(infoTruyen, infoTaiKhoan);
            if(dg != null)
            {
                model.addAttribute("soSaoCu", dg.getDiemSao());
            }
        }
        return "/user/chi_tiet";
    }
    
    @PostMapping("/them-danh-gia")
    public String xuLyThemDanhGia(@RequestParam int truyenId, @RequestParam int diemSao, HttpSession session) {
        String user = (String) session.getAttribute("nguoiDung");
        if(user == null || user.isEmpty())
        {
            return "redirect:/login-user";
        }

        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(user);
        Truyen truyen = bus.layThongTinTruyen(truyenId);

        DanhGia dgMoi = new DanhGia();
        dgMoi.setTruyen(truyen);
        dgMoi.setTaiKhoan(tk);
        dgMoi.setDiemSao(diemSao);

        busDanhGia.themDanhGiaVaCapNhatTruyen(dgMoi);
        return "redirect:/chi-tiet-truyen/" + truyenId;
    }
    
    @PostMapping("/them-binh-luan")
    public String postMethodName(@RequestParam int truyenId, @RequestParam String noiDung, HttpSession session) 
    {
        String user = (String) session.getAttribute("nguoiDung");
        if (user == null || user.isEmpty()) {
            return "redirect:/login-user";
        }

        if(noiDung == null || noiDung.trim().isEmpty()) {
            return "redirect:/chi-tiet-truyen/" + truyenId;
        }

        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(user);
        Truyen truyen = bus.layThongTinTruyen(truyenId);

        BinhLuan bl = new BinhLuan();
        bl.setTaiKhoan(tk);
        bl.setTruyen(truyen);
        bl.setThoiGian(LocalDateTime.now());
        bl.setNoiDung(noiDung);

        busBinhLuan.themBinhLuan(bl);
        return "redirect:/chi-tiet-truyen/" + truyenId;
    }
    
}
