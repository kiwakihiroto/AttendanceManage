package com.example.AttendanceManage.login;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class logoutController {
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // セッションからログインIDを削除
        session.removeAttribute("login_id");

        return "redirect:/login";
    }
}
