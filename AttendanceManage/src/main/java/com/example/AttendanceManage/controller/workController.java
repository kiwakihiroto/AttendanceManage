package com.example.AttendanceManage.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RequestMapping("")
@Controller
public class workController {
    private HttpSession session;
    @Autowired
    public workController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("")
    public String index() {
        System.out.println("aaa");
        return "work";
    }
    @GetMapping("/work")
    public String work(Model model) {
        //System.out.println(this.session.getAttribute("login_id"));
        return "work";
    }

    @PostMapping("/work")
    public String inputScreen_work_start(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 出勤ボタンを押したときに現在の時間を取得
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String formatNowDate = sdf1.format(nowDate);
        System.out.println(formatNowDate);
        //日付取得
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatNowDate2 = sdf2.format(nowDate);
        System.out.println(formatNowDate);

        System.out.println("test");
        System.out.println(this.session.getAttribute("login_id"));
        //sessionをintにキャスト
        //int login_id = (int) this.session.getAttribute("login_id");

        //  出勤時間をDBに追加
        //String sql = "insert into work (login_id,date,start_work) values (" + login_id +" ,'" + formatNowDate2 +"','"+ formatNowDate + "')";
        String sql = "insert into work (login_id,date,start_work) values("+session.getAttribute("login_id")+",'"+formatNowDate2+"','"+formatNowDate+"')";
        jdbcTemplate.update(sql);
        return "place";
    }
//    @PostMapping("/work")
//    public String inputScreen_work_end(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 退勤ボタンを押したときに現在の時間を取得
//        Date nowDate = new Date();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
//        String formatNowDate = sdf1.format(nowDate);
//        System.out.println(formatNowDate);
//        System.out.println("test");
//        System.out.println(this.session.getAttribute("login_id"));
//
//        int login_id = (int) this.session.getAttribute("login_id");
//        Integer i = login_id;
//        Date date = new Date();
//        //  退勤時間をDBに追加
//        String sql = "update work set end_work = ? WHERE login_id = ?;
//        jdbcTemplate.update(sql);
//        return "place";
//    }
}