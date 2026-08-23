package com.flogin.webtruyen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.DanhGiaRepository;
import com.flogin.webtruyen.repository.TruyenRepository;

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

    @Transactional 
    public void themDanhGiaVaCapNhatTruyen(DanhGia dg) {
        DanhGia checkDg = layThongTinDanhGia(dg.getTruyen(), dg.getTaiKhoan());
        
        if (checkDg != null) 
            {
            checkDg.setDiemSao(dg.getDiemSao());
            danhGiaRepo.save(checkDg);

            Truyen truyen = checkDg.getTruyen();

            int luotCu = truyen.getTongSoDanhGia();
            int tongSao = danhGiaRepo.tongSoSao(truyen.getId());
            
            double diemTB = (double) tongSao / luotCu;
            double diemLamTron = Math.round(diemTB * 10.0) / 10.0;
            truyen.setDiemTrungBinh(diemLamTron);

            truyenRepo.save(truyen);
        } else {
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
}
