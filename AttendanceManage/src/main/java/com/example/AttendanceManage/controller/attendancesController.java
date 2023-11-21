package com.example.AttendanceManage.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class attendancesController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/")
    public String address(Model model){
        return "";
    }

    @GetMapping("/condition")
    public String condition(Model model){
//       データベース接続テスト
        String sql = "SELECT * FROM attendances";
        System.out.println(jdbcTemplate.queryForList(sql));

        return "/condition";
    }
    @GetMapping("/")
    public String login(Model model){
        return "";
    }
    @GetMapping("/")
    public String place(Model model){
        return "";
    }
    @GetMapping("/")
    public String work(Model model) {
        return "";
    }
}
