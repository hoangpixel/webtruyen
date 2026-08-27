package com.flogin.webtruyen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.TheLoai;
import com.flogin.webtruyen.repository.TaiKhoanRepository;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class TaiKhoanService {
    @Autowired
    TaiKhoanRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<TaiKhoan> layDanhSachTheoPhanTrang(int trangHienTai, int size)
    {
        Sort sapXepMoiNhat = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(trangHienTai - 1, size, sapXepMoiNhat);
        return repo.findAll(pageable);
    }

    
    public Page<TaiKhoan> timKiemCoBan(int trangHienTai, int size, String username)
    {
        Pageable pageble = PageRequest.of(trangHienTai - 1, size);
        return repo.findByUsernameContainingOrderByIdDesc(username, pageble);
    }

    // public boolean kiemTraTaiKhoan(String username, String password) 
    // {
    //     String hasdedPass = maHoaMatKhau(password);
    //     return repo.existsByUsernameAndPassword(username, hasdedPass);
    // }

    // public TaiKhoan layTaiKhoan(String username, String password) {
    //     String hasdedPass = maHoaMatKhau(password);
    //     return repo.findByUsernameAndPassword(username, hasdedPass);
    // }

    public boolean kiemTraTrungUsername(String username)
    {
        return repo.existsByUsername(username);
    }

    public boolean kiemTraTrungEmail(String email)
    {
        return repo.existsByEmail(email);
    }

    public boolean kiemTraTrungPassword(String password, String confirmPassword)
    {
        return password.equals(confirmPassword);
    }

    public boolean themTaiKhoan(TaiKhoan tk)
    {
        if(tk != null)
        {
            tk.setPassword(passwordEncoder.encode(tk.getPassword()));
            tk.setAvatar("default.jpg");
            tk.setTrangThai(1);
            repo.save(tk);
            return true;
        }
        return false;
    }

    public boolean capNhatTaiKhoan(TaiKhoan tk)
    {
        TaiKhoan tkSua = repo.findById(tk.getId()).orElse(null);
        if(tkSua != null)
        {
            tkSua.setHoTen(tk.getHoTen());
            tkSua.setEmail(tk.getEmail());
            
            if(tk.getAvatar() != null && !tk.getAvatar().isBlank())
            {
                tkSua.setAvatar(tk.getAvatar());
            }

            if(tk.getPassword() != null && !tk.getPassword().isBlank())
            {
                tkSua.setPassword(passwordEncoder.encode(tk.getPassword()));
            }
            repo.save(tkSua);
            return true;
        }
        return false;
    }

    public TaiKhoan layThongTinTaiKhoan(String username)
    {
        return repo.findByUsername(username);
    }

    public TaiKhoan layThongTinTaiKhoanTheoId(int id)
    {
        return repo.findById(id).orElse(null);
    }

    public boolean xoaTaiKhoan(TaiKhoan tk)
    {
        if(tk != null)
        {
            repo.delete(tk);
            return true;
        }
        return false;
    }

    public boolean kiemTraTrungEmailVoiIdKhac(String email, int id)
    {
        return repo.existsByEmailAndIdNot(email, id);
    }

    public long tongTaiKhoan()
    {
        return repo.count();
    }

    public void luuThayDoiTaiKhoan(TaiKhoan tk)
    {
        repo.save(tk);
    }
}
