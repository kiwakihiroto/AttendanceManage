package com.example.AttendanceManage.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
public class placeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    HttpSession session;

    @Autowired
    public placeController(HttpSession session){
        this.session = session;
    }
    @GetMapping("/place")
    public String placeGet(Model model){

        return "place";
    }

    @PostMapping("/place")
    public String placePost(Model model,HttpServletRequest request){
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
        String nowDate = formatDate.format(now);
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        String nowTime = formatTime.format(now);

        String placeInput = request.getParameter("place");
        if(placeInput.equals("0")){
            return "place";
        }

        int login_id = (int) session.getAttribute("login_id");

        String sqlUpdateWork = "update work set work_place_id = "+placeInput+" where login_id = '"+session.getAttribute("login_id")+"' and date = '"+nowDate+"' and end_work is null";
        jdbcTemplate.update(sqlUpdateWork);
        System.out.println();
        return "place";
    }

}
