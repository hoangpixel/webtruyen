package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "vai_tro")
public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Ví dụ: ADMIN, MOD_DUYET_TRUYEN, USER...
    @Column(unique = true, nullable = false)
    private String tenVaiTro; 
    
    private String moTa;

    // 1 Vai Trò sẽ ôm rất nhiều Quyền (Tạo ra bảng phụ vai_tro_quyen)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "vai_tro_quyen",
        joinColumns = @JoinColumn(name = "vai_tro_id"),
        inverseJoinColumns = @JoinColumn(name = "quyen_id")
    )
    private List<Quyen> danhSachQuyen;

    // Quan hệ ngược lại: Vai Trò này đang được cấp cho những Tài Khoản nào
    @ManyToMany(mappedBy = "danhSachVaiTro")
    private List<TaiKhoan> danhSachTaiKhoan;

    public boolean hasQuyen(String tenQuyenCheck) {
        if (this.danhSachQuyen == null) return false;
        for (Quyen q : this.danhSachQuyen) {
            if (q.getTenQuyen().equalsIgnoreCase(tenQuyenCheck)) {
                return true;
            }
        }
        return false;
    }
}