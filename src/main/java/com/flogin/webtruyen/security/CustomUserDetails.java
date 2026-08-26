package com.flogin.webtruyen.security;

import com.flogin.webtruyen.model.Quyen;
import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.VaiTro;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private TaiKhoan taiKhoan;

    public CustomUserDetails(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    // HÀM QUAN TRỌNG NHẤT: Bơm Vai Trò và Quyền vào cho Security hiểu
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        if (taiKhoan.getDanhSachVaiTro() != null) {
            for (VaiTro vt : taiKhoan.getDanhSachVaiTro()) {
                // 1. Bơm Vai trò (Spring Security quy định Vai trò phải có tiền tố ROLE_)
                authorities.add(new SimpleGrantedAuthority("ROLE_" + vt.getTenVaiTro()));
                
                // 2. Bơm luôn các Quyền chi tiết của Vai trò đó
                if (vt.getDanhSachQuyen() != null) {
                    for (Quyen q : vt.getDanhSachQuyen()) {
                        authorities.add(new SimpleGrantedAuthority(q.getTenQuyen()));
                    }
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return taiKhoan.getPassword();
    }

    @Override
    public String getUsername() {
        return taiKhoan.getUsername();
    }

    // Các hàm kiểm tra khóa tài khoản (Mình map với cột trangThai của ông)
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { 
        return taiKhoan.getTrangThai() == 1; // trạng thái = 1 là không khóa
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { 
        return taiKhoan.getTrangThai() == 1; 
    }
    
    // Thêm hàm để lôi thông tin họ tên, avatar ra hiển thị
    public TaiKhoan getTaiKhoan() {
        return this.taiKhoan;
    }
}