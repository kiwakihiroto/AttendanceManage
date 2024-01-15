package com.example.AttendanceManage.adminController;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class userEditController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public userEditController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/admin/userEdit/{loginId}")
    public String getUserEdit(@PathVariable("loginId") String loginId, Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/userEdit";
    }

    @PostMapping("/admin/userEdit")
    public String PostUserEdit(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/userEdit";
    }
}
