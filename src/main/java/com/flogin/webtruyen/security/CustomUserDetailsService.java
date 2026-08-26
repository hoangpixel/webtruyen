package com.flogin.webtruyen.security;

import com.flogin.webtruyen.model.Quyen;
import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.VaiTro;
import com.flogin.webtruyen.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TaiKhoanRepository repoTaiKhoan;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoan tk = repoTaiKhoan.findByUsername(username); 
        
        if (tk == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + username);
        }
        
        // -----------------------------------------------------------------
        // BÍ KÍP ĐÂY: "Đánh thức" (Force Initialize) các danh sách đang bị Lazy
        // -----------------------------------------------------------------
        if (tk.getDanhSachVaiTro() != null) {
            tk.getDanhSachVaiTro().size(); // Ép lôi danh sách Vai Trò lên
            
            for (VaiTro vt : tk.getDanhSachVaiTro()) {
                if (vt.getDanhSachQuyen() != null) {
                    vt.getDanhSachQuyen().size(); // Ép lôi danh sách Quyền lên
                }
            }
        }
        
        return new CustomUserDetails(tk);
    }
}