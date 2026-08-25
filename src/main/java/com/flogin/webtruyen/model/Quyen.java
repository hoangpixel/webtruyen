package com.flogin.webtruyen.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "quyen")
public class Quyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Ví dụ: THEM_TRUYEN, SUA_TRUYEN, XOA_BINH_LUAN...
    @Column(unique = true, nullable = false)
    private String tenQuyen; 
    
    private String moTa;

    // Quan hệ ngược lại: Quyền này đang thuộc về những Vai Trò nào
    @ManyToMany(mappedBy = "danhSachQuyen")
    private List<VaiTro> danhSachVaiTro;
}