package com.example.AttendanceManage.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;
@Controller
public class conditionContoller {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public conditionContoller(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/condition")
    public String condition(Model model){

        String login_id = (String) this.session.getAttribute("login_id");
        System.out.println(this.session.getAttribute("login_id"));

        // ユーザが所属する部署内の氏名、勤務開始時間、勤務終了時間、ステータス、勤務場所。電話番号、メールの表示。
        String sql = "select a.user_name, w.start_work, w.end_work, c.work_condition, p.work_place, a.tel, a.mail \n" +
                "from attendances as a \n" +
                "inner join work as w on a.login_id = w.login_id \n" +
                "inner join condition as c on w.work_condition_id = c.work_condition_id \n" +
                "inner join place as p on w.work_place_id = p.work_place_id \n" +
                "where a.department_id = (select department_id from attendances where login_id = '" + login_id + "')";
        System.out.println(jdbcTemplate.queryForList(sql));

        //一覧表示
        List<Map<String, Object>> attendanceData = jdbcTemplate.queryForList(sql);
        model.addAttribute("condition", attendanceData);
        return "condition";
    }
}
