package com.example.AttendanceManage.controller;
import com.example.AttendanceManage.repository.WorkRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class placeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    WorkRepository workRepository;

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
    public String placePost(Model model,HttpServletRequest request) throws ParseException {
        //現在の日付取得
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
        String nowDateString = formatDate.format(now);
        Date nowDate = formatDate.parse(nowDateString);

        //sessionをStringに
        String login_idString = session.getAttribute("login_id").toString();
        int login_idInt = Integer.parseInt(login_idString);

        //tableに現在の日付のデータがない時
        /*
        String sqlSelectWork = "select count(*) from work where login_id = '"+login_id+"' and date = '"+nowDate+"'";
        int selectNowDateCount = jdbcTemplate.queryForObject(sqlSelectWork,Integer.class);
        System.out.println(selectNowDateCount);
         */
        int selectNowDateCount = workRepository.countWorkByLoginIdAndDate(login_idInt,nowDate);
        if(selectNowDateCount == 0){
            System.out.println("error:placeController|現在の日付のデータがtableにありません");
            return "place";
        }

        //勤務場所を取得
        String placeInput = request.getParameter("place");

        //選択なしの場合何もなしない
        if(placeInput.equals("0")){
            System.out.println("何も選択されませんでした");
            return "place";
        }

        //勤務場所を登録
        /*
        String sqlUpdateWork = "update work set work_place_id = "+placeInput+" where login_id = '"+login_id+"' and date = '"+nowDate+"' and end_work is null";
        jdbcTemplate.update(sqlUpdateWork);
        System.out.println("勤務場所登録完了");
        */
        //workRepository.update(placeInput,login_idInt,nowDate);

        System.out.println("登録完了");
        return "place";
    }
}
