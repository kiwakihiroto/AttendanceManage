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
public class conditionContoller {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/condition")
    public String condition(Model model){
        //       ユーザが所属する部署内にする必要があるが、ログインしたときのデータの持ち方で変わるため、分かり次第変更。
        String sql = "select a.user_name, w.start_work, w.end_work, c.work_condition, p.work_place, a.tel, a.mail \n" +
                "from attendances as a \n" +
                "inner join work as w on a.login_id = w.login_id \n" +
                "inner join condition as c on w.work_condition_id = c.work_condition_id \n" +
                "inner join place as p on w.work_place_id = p.work_place_id";
        System.out.println(jdbcTemplate.queryForList(sql));
    //      一覧表示
        List<Map<String, Object>> attendanceData = jdbcTemplate.queryForList(sql);
        model.addAttribute("condition", attendanceData);
        return "condition";
    }
}
