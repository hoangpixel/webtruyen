package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.BinhLuan;
import com.flogin.webtruyen.repository.BinhLuanRepository;

@Service
public class BinhLuanService {
    @Autowired
    BinhLuanRepository repo;

    public Page<BinhLuan> layDanhSachBinhLuanCoPhanTrang(int trangHienTai, int size)
    {
        Sort desc = Sort.by(Sort.Direction.DESC, "id");
        Pageable pagealbe = PageRequest.of(trangHienTai - 1, size, desc);
        return repo.findAll(pagealbe);
    }

    public Page<BinhLuan> timKiemCoBanBinhLuan(int trangHienTai, int size, String hoTen)
    {
        Pageable pageable = PageRequest.of(trangHienTai - 1, size);
        return repo.findByTaiKhoanHoTenContainingIgnoreCaseOrderByIdDesc(hoTen, pageable);
    }

    public boolean xoaBinhLuan(BinhLuan bl)
    {
        if(bl != null)
        {
            repo.delete(bl);
            return true;
        }
        return false;
    }

    public List<BinhLuan> layDanhSachTheoTruyen(int id) 
    {
        return repo.findByTruyenIdOrderByIdDesc(id);
    }

    public int tongSoLuotBinhLuan(int id)
    {
        return repo.countByTruyenId(id);
    }

    public boolean themBinhLuan(BinhLuan bl)
    {
        if(bl != null)
        {
            repo.save(bl);
            return true;
        }
        return false;
    }

    public BinhLuan layThongTinBinhLuan(int id)
    {
        return repo.findById(id).orElse(null);
    }

    public long tongBinhLuan()
    {
        return repo.count();
    }

    public List<BinhLuan> layDanhSachTop5BinhLuan()
    {
        return repo.findTop5ByOrderByIdDesc();
    }
}
