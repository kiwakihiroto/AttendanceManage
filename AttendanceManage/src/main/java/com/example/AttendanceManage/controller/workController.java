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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("")
    public String index() {
        System.out.println("aaa");
        return "work";
    }
    @GetMapping("/work")
    public String work(Model model) {
        //DB内容取得(仮) login完成次第変更
        String sql = "select a.user_name, w.start_work, w.end_work, c.work_condition, p.work_place, a.tel, a.mail \n" +
                "from attendances as a \n" +
                "inner join work as w on a.login_id = w.login_id \n" +
                "inner join condition as c on w.work_condition_id = c.work_condition_id \n" +
                "inner join place as p on w.work_place_id = p.work_place_id";
        System.out.println(jdbcTemplate.queryForList(sql));
        //      一覧表示
        List<Map<String, Object>> attendanceData = jdbcTemplate.queryForList(sql);
        model.addAttribute("work", attendanceData);
        return "work";
    }

    @PostMapping("/work")
    public String inputScreenSubmit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 出勤ボタンを押したときに現在の時間を取得
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String formatNowDate = sdf1.format(nowDate);
        System.out.println(formatNowDate);

        //  出勤時間をDBに追加
        String sql2 = "insert into attendances (begin_time) values ('" + formatNowDate + "')";
        jdbcTemplate.update(sql2);
        return "work";
    }
}