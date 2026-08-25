package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    private String email;
    private String hoTen;
    private String avatar;

    // 1 = Hoạt động, 0 = Bị khóa
    @Column(columnDefinition = "int default 1")
    private int trangThai; 

    // CHÌA KHÓA PHÂN QUYỀN NẰM Ở ĐÂY:
    // 1 Tài Khoản có thể gánh nhiều Vai Trò (Tạo ra bảng phụ tai_khoan_vai_tro)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "tai_khoan_vai_tro",
        joinColumns = @JoinColumn(name = "tai_khoan_id"),
        inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private List<VaiTro> danhSachVaiTro;

    public boolean hasRole(String tenVaiTroCheck) 
    {
        if (this.danhSachVaiTro == null) return false;
        for (VaiTro vt : this.danhSachVaiTro) 
        {
            if (vt.getTenVaiTro().equalsIgnoreCase(tenVaiTroCheck)) 
            {
                return true;
            }
        }
        return false;
    }

    public String getChucVuString() 
    {
        if (this.danhSachVaiTro == null || this.danhSachVaiTro.isEmpty())
        {
            return "USER";
        }
        return this.danhSachVaiTro.get(0).getTenVaiTro(); 
    }
}