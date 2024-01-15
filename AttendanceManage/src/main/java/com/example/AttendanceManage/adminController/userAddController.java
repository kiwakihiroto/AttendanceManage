package com.example.AttendanceManage.adminController;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class userAddController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public userAddController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/admin/userAdd")
    public String getUserAdd(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/userAdd";
    }

    @PostMapping("/admin/userAdd")
    public String postUserAdd(@RequestParam("status") String status, Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/userAdd";
    }
}
