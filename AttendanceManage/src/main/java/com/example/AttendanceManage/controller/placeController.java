package com.example.AttendanceManage.controller;

import com.example.AttendanceManage.model.User;
import com.example.AttendanceManage.model.Work;
import com.example.AttendanceManage.repository.WorkRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class placeController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    WorkRepository workRepository;

    HttpSession session;
    @Autowired
    private com.example.AttendanceManage.repository.AdminRepository AdminRepository;

    @Autowired
    public placeController(HttpSession session){
        this.session = session;
    }
    @GetMapping("/place")
    public String placeGet(Model model){
        int loginId = Integer.parseInt(session.getAttribute("login_id").toString());
        ///admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(Integer.toString(loginId)));

        return "place";
    }


    @PostMapping("/place")
    public String placePost(Model model,HttpServletRequest request) throws ParseException {
        //現在の日付取得
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
        String nowDateString = formatDate.format(now);
        Date nowDate = formatDate.parse(nowDateString);

        //1日前
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date nowDayAgo = calendar.getTime();

        //sessionをStringに
        String login_idString = session.getAttribute("login_id").toString();

        //admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(login_idString));

        //Stringをintに
        int login_idInt = Integer.parseInt(login_idString);

        //tableに現在の日付のデータがない時
        /*
        String sqlSelectWork = "select count(*) from work where login_id = '"+login_id+"' and date = '"+nowDate+"'";
        List<Work> sqlSelectWorkList = jdbcTemplate.query(sqlSelectWork,new DataClassRowMapper<>(Work.class));

        System.out.println(sqlSelectWorkList);
        */
        int selectNowDateCount = workRepository.countByLoginIdAndDate(login_idInt,nowDate,nowDayAgo);
        System.out.println(selectNowDateCount);
        if(selectNowDateCount == 0){
            System.out.println("error:placeController:|出勤中のデータがtableにありませんでした");
            model.addAttribute("errorPlace","出勤中のデータありませんでした。");
            return "place";
        }else if (selectNowDateCount > 1){
            System.out.println("error:placeController|出勤中のデータが1つ以上あり処理が実行できません");
            model.addAttribute("errorPlace","error");
            return "place";
        }else if(selectNowDateCount == 1){
            System.out.println("正常");
        }


        //勤務場所を取得
        String placeInput = request.getParameter("place");

        //選択なしの場合何もしない
        if(placeInput.equals("0")){
            System.out.println("何も選択されませんでした");
            return "place";
        }

        //勤務場所を登録
        /*
        String sqlUpdateWork = "update work set work_place_id = "+placeInput+" where login_id = '"+login_id+"' and date = '"+nowDate+"' and end_work is null";
        jdbcTemplate.update(sqlUpdateWork);
         */
        workRepository.updateWorkPlaceIdByLoginIdAndDateAndEndWorkIsNull(placeInput,login_idInt,nowDate,nowDayAgo);
        System.out.println("勤務場所登録完了");

        return "place";
    }
}
