package com.flogin.webtruyen.controller.admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flogin.webtruyen.model.AnhChuong;
import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.service.ChuongService;
import com.flogin.webtruyen.service.TruyenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/admin/quan-ly-chuong")
public class AdminChuongController {
    @Autowired
    ChuongService busChuong;

    @Autowired
    TruyenService busTruyen;

    @GetMapping({"", "/"})
    public String loadDanhSachChuong(@RequestParam(name="page", defaultValue = "1") int page,Model model) {
        int soLuong = 12;
        Page<Chuong> pageChuong = busChuong.layDanhSachCoPhanTrang(page, soLuong);
        List<Truyen> danhSachTruyen = busTruyen.layDanhSach();

        model.addAttribute("danhSachTruyen", danhSachTruyen);
        model.addAttribute("danhSachChuong", pageChuong.getContent());
        model.addAttribute("trangHienTai", page);
        model.addAttribute("tongSoTrang", pageChuong.getTotalPages());

        return "admin/chuong_admin";
    }

    @PostMapping("/them-chuong")
    public String xuLyThemChuong(
            @RequestParam("truyenId") int truyenId,
            @RequestParam("soChuong") int soChuong,
            @RequestParam("tenChuong") String tenChuong,
            @RequestParam("filesAnh") MultipartFile[] filesAnh,
            RedirectAttributes redirectAttributes) {

        try {
            Truyen truyen = busTruyen.layThongTinTruyen(truyenId);
            if (truyen == null) {
                redirectAttributes.addFlashAttribute("loi", "Thất bại: Không tìm thấy chân kinh này!");
                return "redirect:/admin/quan-ly-chuong";
            }

            if (busChuong.kiemTraTrungSoChuong(truyenId, soChuong)) 
            {
                redirectAttributes.addFlashAttribute("loi", "Lỗi: Chương " + soChuong + " đã tồn tại trong bộ truyện này!");
                return "redirect:/admin/quan-ly-chuong";
            }

            Chuong chuong = new Chuong();
            chuong.setTruyen(truyen);
            chuong.setSoChuong(soChuong);
            chuong.setTenChuong(tenChuong);
            chuong.setLuotXem(0);

            List<AnhChuong> danhSachAnh = new ArrayList<>();
            int thuTu = 1;

            Path thuMucLuu = Paths.get("src/main/resources/static/images/chuong/" + truyenId);
            Files.createDirectories(thuMucLuu);

            for (MultipartFile file : filesAnh) 
                {
                if (!file.isEmpty()) 
                    {
                    String tenFileGoc = file.getOriginalFilename();
                    String duoiFile = tenFileGoc.substring(tenFileGoc.lastIndexOf("."));
                    
                    String tenFileMoi = "C" + soChuong + "_P" + thuTu + duoiFile;
                    
                    Path duongDan = thuMucLuu.resolve(tenFileMoi);
                    Files.copy(file.getInputStream(), duongDan, StandardCopyOption.REPLACE_EXISTING);

                    AnhChuong anh = new AnhChuong();
                    anh.setTenFileAnh(truyenId + "/" + tenFileMoi); 
                    anh.setThuTu(thuTu);
                    anh.setChuong(chuong); 

                    danhSachAnh.add(anh);
                    thuTu++;
                }
            }

            chuong.setDanhSachAnh(danhSachAnh);
            busChuong.themChuong(chuong);

            redirectAttributes.addFlashAttribute("thongbao", "Đã luyện thành công Chương " + soChuong + " với " + (thuTu - 1) + " trang truyện!");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("loi", "Lỗi tẩu hỏa nhập ma khi lưu ảnh: " + e.getMessage());
        }

        return "redirect:/admin/quan-ly-chuong";
    }
    
    @PostMapping("/sua-chuong")
    public String xuLySuaChuong(@ModelAttribute Chuong chuongCapNhat, RedirectAttributes redirectAttributes) {
        try {
            Chuong chuongCu = busChuong.layThongTinChuong(chuongCapNhat.getId());
            if(chuongCu != null) {
                chuongCu.setSoChuong(chuongCapNhat.getSoChuong());
                chuongCu.setTenChuong(chuongCapNhat.getTenChuong());
                
                busChuong.suaChuong(chuongCu);
                redirectAttributes.addFlashAttribute("thongbao", "Cập nhật thông tin Chương " + chuongCu.getSoChuong() + " thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("loi", "Lỗi cập nhật: " + e.getMessage());
        }
        return "redirect:/admin/quan-ly-chuong";
    }

    @GetMapping("/xoa-chuong/{id}")
    public String xuLyXoaChuong(@PathVariable("id") int id, RedirectAttributes redirectAttributes) 
    {
        Chuong chuong = busChuong.layThongTinChuong(id);
        if(chuong != null) 
            {
            
            try {
                for (AnhChuong anh : chuong.getDanhSachAnh()) {
                    Path duongDanAnh = Paths.get("src/main/resources/static/images/chuong/" + anh.getTenFileAnh());
                    Files.deleteIfExists(duongDanAnh);
                }
            } catch (Exception e) 
            {
                System.out.println("====== LỖI DỌN FILE RÁC: " + e.getMessage());
            }
            busChuong.xoaChuong(chuong);
            redirectAttributes.addFlashAttribute("thongbao", "Đã xuất chiêu xóa thành công chương ID: " + id + " và dọn sạch ổ cứng!");
        } else 
        {
            redirectAttributes.addFlashAttribute("loi", "Không tìm thấy chương này để xóa!");
        }
        
        return "redirect:/admin/quan-ly-chuong";
    }
    
}
