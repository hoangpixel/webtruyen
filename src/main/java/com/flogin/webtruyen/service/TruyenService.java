package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.ChuongRepository;
import com.flogin.webtruyen.repository.TruyenRepository;

@Service
public class TruyenService {
    @Autowired
    TruyenRepository repo;

    @Autowired
    ChuongRepository repoChuong;

    @Autowired
    DanhGiaService repoDanhGia;

    public List<Truyen> layDanhSach()
    {
        List<Truyen> dsTruyen = repo.findAll();
        for(Truyen truyen : dsTruyen)
        {
            Chuong chuong = repoChuong.findTopByTruyenOrderBySoChuongDesc(truyen);
            if(chuong != null)
            {
                truyen.setChuongMoiNhat("Chapter : " + chuong.getSoChuong());
            }else
            {
                truyen.setChuongMoiNhat("Chưa có chương nào");
            }
        }
        return dsTruyen;
    }

    public Truyen layThongTinTruyen(int id)
    {
        return repo.findById(id).orElse(null);
    }

    public List<Truyen> layDanhSachTop10TruyenHot()
    {
        return repo.findTop10ByOrderByLuotXemDesc();
    }
}   
