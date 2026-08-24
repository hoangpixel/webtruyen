package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.model.TruyenYeuThich;
import com.flogin.webtruyen.repository.TaiKhoanRepository;
import com.flogin.webtruyen.repository.TruyenRepository;
import com.flogin.webtruyen.repository.TruyenYeuThichRepository;

public class TruyenYeuThichService {
    @Autowired
    TruyenYeuThichRepository repo;

    @Autowired
    TruyenRepository repoTruyen;
    
    @Autowired
    TaiKhoanRepository repoTaiKhoan;

    public List<TruyenYeuThich> layDanhSachTruyen(Truyen truyen, TaiKhoan taiKhoan)
    {
        return repo.findByTruyenAndTaiKhoan(truyen, taiKhoan);
    }

    public TruyenYeuThich kiemTraDaLuuChua(Truyen truyen, TaiKhoan taiKhoan)
    {
        List<TruyenYeuThich> ds = layDanhSachTruyen(truyen, taiKhoan);
        if(ds.isEmpty())
        {
            return null;
        }
        return ds.getFirst();
    }

    public TruyenYeuThich timTruyenYeuThichTheoId(int id)
    {
        return repo.findById(id).orElse(null);
    }

    public boolean themYeuThich(TruyenYeuThich tyt)
    {
        if(tyt != null)
        {
            repo.save(tyt);
            return true;
        }
        return false;
    }

    public boolean xoaYeuThich(TruyenYeuThich tyt)
    {
        if(tyt != null)
        {
            repo.delete(tyt);
            return true;
        }
        return false;
    }
}
