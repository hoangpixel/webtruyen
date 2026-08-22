package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "truyen")
public class Truyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tenTruyen;
    private String tacGia;
    
    @Column(columnDefinition = "TEXT")
    private String moTa;
    private String anhBia;
    private String trangThai;

    private double diemTrungBinh;
    private int tongSoDanhGia;
    private int luotXem;

    // Quan hệ Nhiều-Nhiều với Thể Loại (Sinh ra bảng phụ truyen_the_loai)
    @ManyToMany
    @JoinTable(
        name = "truyen_the_loai",
        joinColumns = @JoinColumn(name = "truyen_id"),
        inverseJoinColumns = @JoinColumn(name = "the_loai_id")
    )
    private List<TheLoai> danhSachTheLoai;

    @OneToMany(mappedBy = "truyen", cascade = CascadeType.ALL)
    private List<Chuong> danhSachChuong;
}