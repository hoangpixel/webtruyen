package com.flogin.webtruyen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.DanhGiaRepository;
import com.flogin.webtruyen.repository.TruyenRepository;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;

@Service
public class DanhGiaService {
    @Autowired
    DanhGiaRepository danhGiaRepo;

    @Autowired
    TruyenRepository truyenRepo;

    public DanhGia layThongTinDanhGia(Truyen truyen, TaiKhoan taiKhoan)
    {
        return danhGiaRepo.findTopByTruyenAndTaiKhoanOrderByIdDesc(truyen, taiKhoan);
    }

    public Page<DanhGia> layDanhSachDanhGiaTheoPhanTrang(int trangHienTai, int size)
    {
        Sort desc = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(trangHienTai - 1, size, desc);
        return danhGiaRepo.findAll(pageable);
    }

    public Page<DanhGia> timKiemCoBanDanhGia(int trangHienTai, int size, String hoTen)
    {
        Pageable pageable = PageRequest.of(trangHienTai - 1, size);
        return danhGiaRepo.findByTaiKhoanHoTenContainingIgnoreCaseOrderByIdDesc(hoTen, pageable);
    }

    public DanhGia layThongTinDanhGiaTheoId(int id)
    {
        return danhGiaRepo.findById(id).orElse(null);
    }

    public boolean xoaDanhGia(DanhGia dg) {
        try {
            Truyen truyen = dg.getTruyen();
            
            danhGiaRepo.delete(dg);

            Integer tongSoSao = danhGiaRepo.tongSoSao(truyen.getId());
            if (tongSoSao == null) tongSoSao = 0; 
            
            long soLuotDanhGiaConLai = danhGiaRepo.countByTruyen(truyen); 

            if (soLuotDanhGiaConLai > 0) {
                double diemTB = (double) tongSoSao / soLuotDanhGiaConLai;
                double lamTron = Math.round(diemTB * 10.0) / 10.0;
                truyen.setDiemTrungBinh(lamTron);
            } else {
                truyen.setDiemTrungBinh(0.0);
            }
            
            truyen.setTongSoDanhGia((int) soLuotDanhGiaConLai);
            
            truyenRepo.save(truyen);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional 
    public void themDanhGiaVaCapNhatTruyen(DanhGia dg) {
        DanhGia checkDg = layThongTinDanhGia(dg.getTruyen(), dg.getTaiKhoan());
        
        if (checkDg != null) {
            // Cập nhật lại số sao
            checkDg.setDiemSao(dg.getDiemSao());
            danhGiaRepo.save(checkDg);

            Truyen truyen = checkDg.getTruyen();
            int luotCu = truyen.getTongSoDanhGia();
            
            // 🚨 CHỐT CHẶN BẢO VỆ CHIA CHO 0
            // Nếu phát hiện DB bị ảo (có đánh giá mà tổng lượt = 0)
            if (luotCu <= 0) {
                luotCu = 1;
                truyen.setTongSoDanhGia(1); // Fix luôn lỗi data trong DB
            }

            int tongSao = danhGiaRepo.tongSoSao(truyen.getId());
            
            double diemTB = (double) tongSao / luotCu;
            double diemLamTron = Math.round(diemTB * 10.0) / 10.0;
            truyen.setDiemTrungBinh(diemLamTron);

            truyenRepo.save(truyen);
        } else {
            // Thêm mới hoàn toàn
            danhGiaRepo.save(dg);
            Truyen truyen = dg.getTruyen();
            
            int tongSoSao = danhGiaRepo.tongSoSao(truyen.getId());
            int tongSoLuotDanhGia = truyen.getTongSoDanhGia() + 1;
            
            truyen.setTongSoDanhGia(tongSoLuotDanhGia);

            double diemTB = (double) tongSoSao / tongSoLuotDanhGia;
            double diemLamTron = Math.round(diemTB * 10.0) / 10.0;

            truyen.setDiemTrungBinh(diemLamTron);
            truyenRepo.save(truyen);
        }
    }

    public long tongDanhGia()
    {
        return danhGiaRepo.count();
    }
}
