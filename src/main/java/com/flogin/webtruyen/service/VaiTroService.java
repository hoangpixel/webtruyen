package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.VaiTro;
import com.flogin.webtruyen.repository.VaiTroRepository;

@Service
public class VaiTroService {
    @Autowired 
    VaiTroRepository repo;

    public List<VaiTro> layDanhSach() { return repo.findAll(); }
    public VaiTro layThongTin(int id) { return repo.findById(id).orElse(null); }
    
    public boolean kiemTraTrungTen(String ten) { return repo.existsByTenVaiTro(ten); }
    public boolean kiemTraTrungLucSua(String ten, int id) { return repo.existsByTenVaiTroAndIdNot(ten, id); }
    
    public boolean luuVaiTro(VaiTro vt) { 
        if(vt != null) { repo.save(vt); return true; } 
        return false; 
    }
    public boolean xoaVaiTro(VaiTro vt) { 
        if(vt != null) { repo.delete(vt); return true; } 
        return false; 
    }
}