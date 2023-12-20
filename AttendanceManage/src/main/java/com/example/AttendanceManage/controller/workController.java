package com.example.AttendanceManage.controller;
import com.example.AttendanceManage.model.User;
import com.example.AttendanceManage.model.Work;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RequestMapping("")
@Controller
public class workController {
    private HttpSession session;
    @Autowired
    public workController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("")
    public String index() {
        System.out.println("aaa");
        return "work";
    }
    @GetMapping("/work")
    public String work(Model model) {
        //System.out.println(this.session.getAttribute("login_id"));
        return "work";
    }

    @PostMapping("/work")
    public String inputScreen_work_start(@RequestParam("status")String status, Model model){
        //現在の時間を取得
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String formatNowDate = sdf1.format(nowDate);
        System.out.println(formatNowDate);
        //日付取得
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatNowDate2 = sdf2.format(nowDate);
        System.out.println(formatNowDate);
        //sessionをString型に
        String login_id = (String) this.session.getAttribute("login_id");

        if("出勤".equals(status)) {
            //  出勤時間をDBに追加
            String sql = "insert into work (login_id,date,start_work) values (" + login_id + " ,'" + formatNowDate2 + "','" + formatNowDate + "')";
            jdbcTemplate.update(sql);
            System.out.println(status);

        }else if("退勤".equals(status)){
            String sql2 = "select end_work from work where login_id = '"+login_id+"'  and date = '"+formatNowDate2+"' and end_work is null" ;
            List<Work> works = jdbcTemplate.query(sql2,new DataClassRowMapper<>(Work.class));
            System.out.println(works.get(0).getEndWork());
            if(works.get(0).getEndWork() == null){
                System.out.println("null");
                String sql = "update work set end_work = '"+formatNowDate+"' where login_id = '"+login_id+"' and date = '"+formatNowDate2+"' ";
                jdbcTemplate.update(sql);
            }
            else {
                model.addAttribute("error", "失敗しました");
            }
            System.out.println(status);

        }else if("休憩開始".equals(status)) {
            String sql = "update work set start_break= '"+formatNowDate+"' where login_id = '"+login_id+"' and date = '"+formatNowDate2+"' ";
            jdbcTemplate.update(sql);
            System.out.println(status);
        }else if("休憩終了".equals(status)){
            String sql = "update work set end_break = '"+formatNowDate+"' where login_id = '"+login_id+"' and date = '"+formatNowDate2+"' ";
            jdbcTemplate.update(sql);
            System.out.println(status);
        }
        return "work";
    }
//    @PostMapping("/work")
//    public String inputScreen_work_end(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 退勤ボタンを押したときに現在の時間を取得
//        Date nowDate = new Date();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
//        String formatNowDate = sdf1.format(nowDate);
//        System.out.println(formatNowDate);
//        //日付取得
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
//        String formatNowDate2 = sdf2.format(nowDate);
//        System.out.println(formatNowDate);
//
//        System.out.println("test");
//        System.out.println(this.session.getAttribute("login_id"));
//
//        //int login_id = (int) this.session.getAttribute("login_id");
//
//        //  退勤時間をDBに追加
////        String sql2 = "insert into work (login_id,date,end_work) values (" + session.getAttribute("login_id") +" ,'" + formatNowDate2 +"','"+ formatNowDate + "')";
//        String sql2 = "update work set end_work = ? WHERE login_id = ?";
//        jdbcTemplate.update(sql2);
//        return "work";
//    }
}