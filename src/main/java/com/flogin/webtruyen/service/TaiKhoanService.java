package com.flogin.webtruyen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.repository.TaiKhoanRepository;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class TaiKhoanService {
    @Autowired
    TaiKhoanRepository repo;

    public boolean kiemTraTaiKhoan(String username, String password) 
    {
        String hasdedPass = maHoaMatKhau(password);
        return repo.existsByUsernameAndPassword(username, hasdedPass);
    }

    public TaiKhoan layTaiKhoan(String username, String password) {
        String hasdedPass = maHoaMatKhau(password);
        return repo.findByUsernameAndPassword(username, hasdedPass);
    }

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

    public String maHoaMatKhau(String password) 
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash); 
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu", ex);
        }
    }

    public boolean themTaiKhoan(TaiKhoan tk)
    {
        if(tk != null)
        {
            String hashedPass = maHoaMatKhau(tk.getPassword());
            tk.setPassword(hashedPass);
            repo.save(tk);
            return true;
        }
        return false;
    }
}
