package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "truyen_yeu_thich")
public class TruyenYeuThich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tai_khoan_id")
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "truyen_id")
    private Truyen truyen;

    private Date ngayLuu;
}