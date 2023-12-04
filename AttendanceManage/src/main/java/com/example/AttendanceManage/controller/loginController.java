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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class loginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) throws ServletException, IOException{
        String loginId = request.getParameter("login_id");
        String pass = request.getParameter("password");

        System.out.println(loginId);
        System.out.println(pass);

        return "login";
    }

}
