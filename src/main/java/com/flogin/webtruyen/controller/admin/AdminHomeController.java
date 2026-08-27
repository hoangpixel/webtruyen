package com.flogin.webtruyen.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flogin.webtruyen.model.BinhLuan;
import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.service.BinhLuanService;
import com.flogin.webtruyen.service.ChuongService;
import com.flogin.webtruyen.service.DanhGiaService;
import com.flogin.webtruyen.service.TaiKhoanService;
import com.flogin.webtruyen.service.TruyenService;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    @Autowired
    TruyenService busTruyen;
    @Autowired
    ChuongService busChuong;
    @Autowired
    TaiKhoanService busTaiKhoan;
    @Autowired
    BinhLuanService busBinhLuan;
    @Autowired
    DanhGiaService busDanhGia;

    @GetMapping({"", "/"})
    public String trangChuAdmin(Model model) {

        // 1. Đổ dữ liệu Thẻ Bài (Cards)
        model.addAttribute("tongTruyen", busTruyen.tongTruyen());
        model.addAttribute("tongChuong", busChuong.tongChuong());
        model.addAttribute("tongTaiKhoan", busTaiKhoan.tongTaiKhoan());
        model.addAttribute("tongBinhLuan", busBinhLuan.tongBinhLuan());
        model.addAttribute("tongDanhGia", busDanhGia.tongDanhGia());

        // 2. Dữ liệu Bảng Top Truyện & Biểu đồ Cột (Bar Chart)
        List<Truyen> danhSachTop = busTruyen.layDanhSachTop5TruyenHot();
        model.addAttribute("topTruyen", danhSachTop);

        List<String> tenTruyenBarChart = new ArrayList<>();
        List<Integer> luotXemBarChart = new ArrayList<>();
        for(Truyen truyen : danhSachTop) {
            tenTruyenBarChart.add(truyen.getTenTruyen());
            luotXemBarChart.add(truyen.getLuotXem());
        }
        // FIX: Bổ sung đẩy dữ liệu Bar Chart xuống View
        model.addAttribute("tenTruyenBarChart", tenTruyenBarChart);
        model.addAttribute("luotXemBarChart", luotXemBarChart);

        // 3. Dữ liệu Bảng Tương Tác Mới Nhất
        List<BinhLuan> binhLuanMoi = busBinhLuan.layDanhSachTop5BinhLuan();
        model.addAttribute("binhLuanMoi", binhLuanMoi);

// 4. Xử lý dữ liệu Biểu đồ Tròn (Pie Chart) bằng Object[]
        List<Object[]> duLieuPie = busTruyen.thongKeTruyenTheoTheLoai(); 
        List<String> tenTheLoaiPieChart = new ArrayList<>();
        List<Long> soLuongPieChart = new ArrayList<>(); 

        if (duLieuPie != null) {
            for(Object[] row : duLieuPie) {
                // Ép kiểu an toàn sang chuỗi (tránh null)
                tenTheLoaiPieChart.add(String.valueOf(row[0])); 
                
                // Bao bọc an toàn: Mọi loại số (Int, Long, BigInt) đều ép về Number trước rồi lấy giá trị Long
                Number count = (Number) row[1];
                soLuongPieChart.add(count.longValue());      
            }
        }
        model.addAttribute("tenTheLoaiPieChart", tenTheLoaiPieChart);
        model.addAttribute("soLuongPieChart", soLuongPieChart);
        
        return "admin/tong_quan_admin";
    }
}