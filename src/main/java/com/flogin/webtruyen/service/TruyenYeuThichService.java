package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.model.TruyenYeuThich;
import com.flogin.webtruyen.repository.TaiKhoanRepository;
import com.flogin.webtruyen.repository.TruyenRepository;
import com.flogin.webtruyen.repository.TruyenYeuThichRepository;

@Service
public class TruyenYeuThichService {
    @Autowired
    TruyenYeuThichRepository repo;

    @Autowired
    TruyenRepository repoTruyen;
    
    @Autowired
    TaiKhoanRepository repoTaiKhoan;

    public Page<TruyenYeuThich> layDanhSachTruyen(int trangHienTai, int size, TaiKhoan taiKhoan)
    {
        Sort sapXepMoiNhat = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(trangHienTai - 1, size, sapXepMoiNhat);
        return repo.findByTaiKhoan(taiKhoan, pageable);
    }

    public TruyenYeuThich kiemTraDaLuuChua(Truyen truyen, TaiKhoan taiKhoan)
    {
        List<TruyenYeuThich> ds = repo.findByTruyenAndTaiKhoan(truyen, taiKhoan);
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

    @Transactional
    public boolean xoaHetListYeuThich(TaiKhoan tk)
    {
        if(tk == null)
        {
            return false;
        }
    
        if(repo.countByTaiKhoan(tk) == 0)
        {
            return false;
        }
        
        repo.deleteByTaiKhoan(tk);

        return true;
    }

    public int tongSoTruyenDaLuu(TaiKhoan tk)
    {
        return (int) repo.countByTaiKhoan(tk);
    }
}
