package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "the_loai")
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tenTheLoai;

    @ManyToMany(mappedBy = "danhSachTheLoai")
    private List<Truyen> danhSachTruyen;

    @Column(columnDefinition = "TEXT")
    private String moTa;
}