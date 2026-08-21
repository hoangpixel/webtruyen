package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "anh_chuong")
public class AnhChuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "chuong_id")
    private Chuong chuong;

    private int thuTu; // 1, 2, 3... để sắp xếp ảnh
    private String tenFileAnh; // Hình ảnh của trang truyện
}