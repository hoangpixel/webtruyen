package com.flogin.webtruyen.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.model.TruyenYeuThich;
import com.flogin.webtruyen.service.TaiKhoanService;
import com.flogin.webtruyen.service.TruyenService;
import com.flogin.webtruyen.service.TruyenYeuThichService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class TruyenYeuThichController {
    @Autowired
    TruyenYeuThichService busTruyenYeuThich;

    @Autowired
    TaiKhoanService busTaiKhoan;

    @Autowired
    TruyenService busTruyen;

    @GetMapping("/truyen-yeu-thich")
    public String loadDanhSachYeuThich(@RequestParam(name="page", defaultValue = "1") int page ,Model model, HttpSession session, Authentication auth) 
    {
        int tongSoTrang = 2;
        if(auth == null)
        {
            return "redirect:/dang-nhap";
        }
        String user = auth.getName();
        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(user);
        Page<TruyenYeuThich> pageList = busTruyenYeuThich.layDanhSachTruyen(page, tongSoTrang, tk);

        int tongSoTruyenDaLuu = busTruyenYeuThich.tongSoTruyenDaLuu(tk);

        model.addAttribute("danhSachYeuThich", pageList.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageList.getTotalPages());
        model.addAttribute("tongTruyenDaLuu", tongSoTruyenDaLuu);

        return "/user/list_yeu_thich";
    }

    @GetMapping("/luu-truyen-yeu-thich/{id}")
    public String luuTruyenYeuThich(@PathVariable("id") int truyenId, HttpSession session, Model model, Authentication auth, RedirectAttributes re) {
        if(auth == null)
        {
            return "redirect:/dang-nhap";
        }
        String user = auth.getName();

        Truyen truyen = busTruyen.layThongTinTruyen(truyenId);
        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(user);

        TruyenYeuThich tyt = busTruyenYeuThich.kiemTraDaLuuChua(truyen, tk);

        if(tyt == null)
        {
            Date time = new Date();

            TruyenYeuThich tytNew = new TruyenYeuThich();
            tytNew.setTaiKhoan(tk);
            tytNew.setTruyen(truyen);
            tytNew.setNgayLuu(time);

            busTruyenYeuThich.themYeuThich(tytNew);
            re.addFlashAttribute("thongbao", "Lưu thành công truyện " + truyen.getTenTruyen() + " vào mục yêu thích!");
            return "redirect:/chi-tiet-truyen/" + truyenId;
        }

        re.addFlashAttribute("loi", "Bạn đã lưu truyện này rồi!");
        return "redirect:/chi-tiet-truyen/" + truyenId;
    }
    
    @GetMapping("/bo-luu-truyen-yeu-thich/{id}")
    public String boLuuTruyenYeuThich(@PathVariable("id") int id, RedirectAttributes re) {
        TruyenYeuThich tyt = busTruyenYeuThich.timTruyenYeuThichTheoId(id);
        if(tyt != null)
        {
            busTruyenYeuThich.xoaYeuThich(tyt);
            re.addFlashAttribute("thongbao", "Xóa thành công khỏi mục yêu thích!");
            return "redirect:/truyen-yeu-thich";
        }
        return "redirect:/truyen-yeu-thich";
    }
    
    @GetMapping("/bo-luu-tat-ca")
    public String boLuuTatCaTruyen(Authentication auth, RedirectAttributes re) {
        String user = auth.getName();
        TaiKhoan tk = busTaiKhoan.layThongTinTaiKhoan(user);
        if(tk != null)
        {
            busTruyenYeuThich.xoaHetListYeuThich(tk);
            re.addFlashAttribute("thongbao", "Xóa thành công tất cả truyện khỏi mục yêu thích!");
            return "redirect:/truyen-yeu-thich";
        }
        return "redirect:/truyen-yeu-thich";
    }
    
}
