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
public class addressController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/address")
    public String address(Model model) {
        return "";
    }

    @PostMapping("/work")
    public String input_start_work(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 出勤ボタンを押したときに現在の時間を取得
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String formatNowDate = sdf1.format(nowDate);
        System.out.println(formatNowDate);

        //  出勤時間をDBに追加
        String sql = "insert into attendance (start_work) values ('" + formatNowDate + "')";
        jdbcTemplate.update(sql);

        //  勤怠状況一覧の表示
        return "/attendanceList";

    }
}