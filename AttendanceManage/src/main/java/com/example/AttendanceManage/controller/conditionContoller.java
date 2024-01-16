package com.example.AttendanceManage.controller;

import com.example.AttendanceManage.login.loginUserService;
import com.example.AttendanceManage.repository.AdminRepository;
import com.example.AttendanceManage.repository.ConditionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class conditionContoller {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    private com.example.AttendanceManage.repository.ConditionRepository ConditionRepository;
    @Autowired
    private com.example.AttendanceManage.repository.AdminRepository AdminRepository;
    @Autowired
    public conditionContoller(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/condition")
    public String condition(Model model){

        String login_id = (String) this.session.getAttribute("login_id");
        //admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(login_id));

        //日付取得
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatNowDate = sdf.format(nowDate);
        model.addAttribute("date",formatNowDate);

        // ユーザが所属する部署内の氏名、勤務開始時間、勤務終了時間、ステータス、勤務場所。電話番号、メールの表示(当日)。
        model.addAttribute("condition", ConditionRepository.getAttendanceData(login_id, formatNowDate));
        return "condition";
    }
}
