package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "danh_gia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "truyen_id")
    private Truyen truyen;

    @ManyToOne
    @JoinColumn(name = "tai_khoan_id")
    private TaiKhoan taiKhoan;

    private int diemSao; // 1 đến 5
    
    @Column(columnDefinition = "TEXT")
    private String nhanXet;
}