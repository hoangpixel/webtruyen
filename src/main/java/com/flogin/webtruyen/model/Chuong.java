package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "chuong")
public class Chuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "truyen_id")
    private Truyen truyen;

    private int soChuong;
    private String tenChuong;
    private int luotXem;

    @OneToMany(mappedBy = "chuong", cascade = CascadeType.ALL)
    private List<AnhChuong> danhSachAnh;
}