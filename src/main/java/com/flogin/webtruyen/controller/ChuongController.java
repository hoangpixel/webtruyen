package com.flogin.webtruyen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.flogin.webtruyen.model.AnhChuong;
import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.service.AnhChuongService;
import com.flogin.webtruyen.service.ChuongService;
import com.flogin.webtruyen.service.TruyenService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ChuongController {
    @Autowired
    ChuongService busChuong;

    @Autowired
    AnhChuongService busAnhChuong;

    @Autowired
    TruyenService busTruyen;

    @GetMapping("/doc-truyen/{slug}/{idTruyen}/{idChuong}")
    public String loadDanhSachChuong(@PathVariable("idTruyen") int idTruyen, @PathVariable("idChuong") int idChuong, Model model) 
    {
        Truyen truyen = busTruyen.layThongTinTruyen(idTruyen);
        Chuong chuong = busChuong.layChuong(idChuong);
        List<Chuong> dsChuong = busChuong.layDanhSachChuongTheoTruyen(truyen);
        List<AnhChuong> dsAnhChuong = busAnhChuong.layDanhSachAnhTheoChuong(chuong);

        Chuong chuongTruoc = null;
        Chuong chuongSau = null;

        for (int i = 0; i < dsChuong.size(); i++) 
        {
            if (dsChuong.get(i).getId() == idChuong) {

                if (i < dsChuong.size() - 1) 
                {
                    chuongTruoc = dsChuong.get(i + 1);
                }
                if (i > 0) 
                {
                    chuongSau = dsChuong.get(i - 1);
                }
                break;
            }
        }

        model.addAttribute("chuongTruoc", chuongTruoc);
        model.addAttribute("chuongSau", chuongSau);

        model.addAttribute("truyen", truyen);
        model.addAttribute("chuong", chuong);

        model.addAttribute("dsChuong", dsChuong);
        model.addAttribute("dsAnhChuong", dsAnhChuong);

        return "/user/doc_truyen";
    }
    
}
