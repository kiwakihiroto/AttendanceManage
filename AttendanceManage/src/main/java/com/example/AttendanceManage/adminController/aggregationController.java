package com.example.AttendanceManage.adminController;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class aggregationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public aggregationController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/admin/aggregation")
    public String GetAggregation(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/aggregation";
    }

    @PostMapping("/admin/aggregation")
    public String postAggregation(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/aggregation";
    }
}
