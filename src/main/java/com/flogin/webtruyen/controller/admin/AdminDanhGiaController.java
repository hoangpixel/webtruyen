package com.flogin.webtruyen.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flogin.webtruyen.model.BinhLuan;
import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.service.BinhLuanService;
import com.flogin.webtruyen.service.DanhGiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/quan-ly-danh-gia-va-binh-luan")
public class AdminDanhGiaController {
    @Autowired
    BinhLuanService busBinhLuan;

    @Autowired
    DanhGiaService busDanhGia;

    @GetMapping({"", "/"})
    public String loadDanhSachBLvaDG(
            @RequestParam(name = "search", required = false) String tuKhoa,
            @RequestParam(name = "tab", defaultValue = "binhluan") String tab,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        int soLuong = 10;
        int pageBL = tab.equals("binhluan") ? page : 1;
        int pageDG = tab.equals("danhgia") ? page : 1;

        Page<BinhLuan> pageBinhLuan;
        Page<DanhGia> pageDanhGia;

        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            pageBinhLuan = busBinhLuan.timKiemCoBanBinhLuan(pageBL, soLuong, tuKhoa);
            pageDanhGia = busDanhGia.timKiemCoBanDanhGia(pageDG, soLuong, tuKhoa);
            model.addAttribute("tuKhoa", tuKhoa);
        } 
        else {
            pageBinhLuan = busBinhLuan.layDanhSachBinhLuanCoPhanTrang(pageBL, soLuong);
            pageDanhGia = busDanhGia.layDanhSachDanhGiaTheoPhanTrang(pageDG, soLuong);
        }

        model.addAttribute("danhSachBinhLuan", pageBinhLuan.getContent());
        model.addAttribute("trangHienTaiBL", pageBL);
        model.addAttribute("tongSoTrangBL", pageBinhLuan.getTotalPages());

        model.addAttribute("danhSachDanhGia", pageDanhGia.getContent());
        model.addAttribute("trangHienTaiDG", pageDG);
        model.addAttribute("tongSoTrangDG", pageDanhGia.getTotalPages());

        model.addAttribute("activeTab", tab);

        return "admin/danh_gia_admin";
    }
    

    @GetMapping("/xoa-binh-luan/{id}")
    public String xuLyXoaBinhLuan(@PathVariable("id") int id, RedirectAttributes re) {
        BinhLuan bl = busBinhLuan.layThongTinBinhLuan(id);
        if(bl != null)
        {
            if(busBinhLuan.xoaBinhLuan(bl))
            {
                 re.addFlashAttribute("thongbao", "Xóa bình luận thành công!");
                 return "redirect:/admin/quan-ly-danh-gia-va-binh-luan?tab=binhluan";
            }else
            {
                re.addFlashAttribute("loi", "Xóa bình luận thất bại!");
                return "redirect:/admin/quan-ly-danh-gia-va-binh-luan?tab=binhluan";
            }
        }
        return "redirect:/admin/quan-ly-danh-gia-va-binh-luan?tab=binhluan";
    }
    
    @GetMapping("/xoa-danh-gia/{id}")
    public String xuLyXoaDanhGia(@PathVariable("id") int id, RedirectAttributes re) {
        DanhGia dg = busDanhGia.layThongTinDanhGiaTheoId(id);
        if(dg != null)
        {
            if(busDanhGia.xoaDanhGia(dg))
            {
                re.addFlashAttribute("thongbao", "Xóa đánh giá thành công!");
                return "redirect:/admin/quan-ly-danh-gia-va-binh-luan?tab=danhgia";
            }else
            {
                re.addFlashAttribute("loi", "Xóa đánh giá thất bại!");
                return "redirect:/admin/quan-ly-danh-gia-va-binh-luan?tab=danhgia";
            }
        }
        return "redirect:/admin/quan-ly-danh-gia-va-binh-luan?tab=danhgia";
    }
    
}