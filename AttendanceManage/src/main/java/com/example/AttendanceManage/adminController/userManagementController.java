package com.example.AttendanceManage.adminController;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class userManagementController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public userManagementController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @GetMapping("/admin/userManagement")
    public String getUserManagement(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        //名前の取得
        String getUserNameSql = "select user_name from attendances where login_id = '" + login_id +"'";
        // spl文から値を取得
        List<Map<String, Object>> name = jdbcTemplate.queryForList(getUserNameSql);
        //list[0]の値を取得
        Map<String, Object> getUserName = name.get(0);
        //user_nameを指定して値の取得
        String userName = (String) getUserName.get("user_name");
        model.addAttribute("userName", userName);

        String getUserSummarySql = "select user_name from attendances";
        List<Map<String, Object>> summaryData = jdbcTemplate.queryForList(getUserSummarySql);
        model.addAttribute("userManagement", summaryData);

        return "/admin/userManagement";
    }

    @PostMapping("/admin/userManagement")
    public String PostUserManagement(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        //名前の取得
        String getUserNameSql = "select user_name from attendances where login_id = '" + login_id +"'";
        // spl文から値を取得
        List<Map<String, Object>> name = jdbcTemplate.queryForList(getUserNameSql);
        //list[0]の値を取得
        Map<String, Object> getUserName = name.get(0);
        //user_nameを指定して値の取得
        String userName = (String) getUserName.get("user_name");
        model.addAttribute("userName", userName);

        String getUserSummarySql = "select user_name from attendances";
        List<Map<String, Object>> summaryData = jdbcTemplate.queryForList(getUserSummarySql);
        model.addAttribute("userManagement", summaryData);

        return "/admin/userManagement";
    }
}