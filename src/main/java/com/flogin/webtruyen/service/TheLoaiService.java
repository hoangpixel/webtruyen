package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.TheLoai;
import com.flogin.webtruyen.repository.TheLoaiRepository;

@Service
public class TheLoaiService {
    @Autowired
    TheLoaiRepository repo;

    public Page<TheLoai> layDanhSachTheoPhanTrang(int trangHienTai, int size)
    {
        Sort asc = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(trangHienTai - 1, size, asc);
        return repo.findAll(pageable);
    }

    public List<TheLoai> layDanhSachTheLoaiTheoId(List<Integer> ids)
    {
        return repo.findAllById(ids);
    }

    public Page<TheLoai> timKiemCoBan(int trangHienTai, int size, String tenTheLoai)
    {
        Pageable pageble = PageRequest.of(trangHienTai - 1, size);
        return repo.findByTenTheLoaiContainingOrderByIdDesc(tenTheLoai, pageble);
    }

    public List<TheLoai> layDanhSach()
    {
        return repo.findAll();
    }

    public boolean themTheLoai(TheLoai tl)
    {
        if(tl != null)
        {
            repo.save(tl);
            return true;
        }
        return false;
    }

    public boolean suaTheLoai(TheLoai tl)
    {
        if(tl != null)
        {
            repo.save(tl);
            return true;
        }
        return false;
    }

    public boolean xoaTheLoai(TheLoai tl)
    {
        if(tl != null)
        {
            repo.delete(tl);
            return true;
        }
        return false;
    }

    public boolean kiemTraTrungTenTheLoai(String ten) 
    {
        return repo.existsByTenTheLoai(ten);
    }

    public boolean kiemTraTrungTenLucSua(String ten, int id) 
    {
        return repo.existsByTenTheLoaiAndIdNot(ten, id);
    }

    public TheLoai layThongTinTheLoai(int id)
    {
        return repo.findById(id).orElse(null);
    }
}
