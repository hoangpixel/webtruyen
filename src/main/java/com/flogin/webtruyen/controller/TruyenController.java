package com.flogin.webtruyen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.service.TruyenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TruyenController {
    @Autowired
    TruyenService bus;

    @GetMapping({"/index", "/"})
    public String loadDanhSachTruyen(Model model) {
        List<Truyen> ds = bus.layDanhSach();
        model.addAttribute("danhSachTruyen", ds);
        return "index";
    }
    
}
