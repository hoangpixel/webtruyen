package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.BinhLuan;
import com.flogin.webtruyen.repository.BinhLuanRepository;

@Service
public class BinhLuanService {
    @Autowired
    BinhLuanRepository repo;

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
}
