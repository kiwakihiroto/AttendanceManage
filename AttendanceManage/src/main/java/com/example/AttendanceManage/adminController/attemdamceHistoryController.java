package com.example.AttendanceManage.adminController;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class attemdamceHistoryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public attemdamceHistoryController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/admin/attemdamceHistory")
    public String getAttemdamceHistory(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/attemdamceHistory";
    }

    @PostMapping("/admin/attemdamceHistory")
    public String postAttemdamceHistory(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/attemdamceHistory";
    }
}
