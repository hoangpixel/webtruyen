package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.ChuongRepository;

@Service
public class ChuongService {
    @Autowired
    ChuongRepository repoChuong;

    public List<Chuong> layDanhSachChuongTheoTruyen(Truyen truyen)
    {
        return repoChuong.findByTruyenOrderBySoChuongDesc(truyen);
    }

    public Chuong layChuong(int id)
    {
        return repoChuong.findById(id).orElse(null);
    }

    public void themLuotXemChuong(Chuong chuong) 
    {
        if (chuong != null) {
            chuong.setLuotXem(chuong.getLuotXem() + 1);
            repoChuong.save(chuong);
        }
    }
}
