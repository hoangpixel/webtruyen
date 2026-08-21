package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String vaiTro;
}