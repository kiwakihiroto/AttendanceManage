package com.example.AttendanceManage.controller;
import com.example.AttendanceManage.model.Work;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "work";
    }

    @GetMapping("/work")
    public String work(Model model) {
        //sessionをString型に
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

        //日付取得
        Date nowDate = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatNowDate2 = sdf2.format(nowDate);

        //出勤状態の取得
        String getWorkConditonSql = "select work_condition_id from work where login_id = '" + login_id + "' and date = '" + formatNowDate2 + "'";
        List<Map<String, Object>> workCondition = jdbcTemplate.queryForList(getWorkConditonSql);
        if (workCondition.isEmpty()){
            model.addAttribute("availability", "none");
        }else{
            Map<String, Object> getWorConditon = workCondition.get(0);
            String workConditionId = (String) getWorConditon.get("work_condition_id");
            model.addAttribute("availability", workConditionId);
        }

        return "work";
    }

    @PostMapping("/work")
    public String inputScreen_work_start(@RequestParam("status") String status, Model model) {
        //現在の時間を取得
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String formatNowDate = sdf1.format(nowDate);

        //日付取得
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatNowDate2 = sdf2.format(nowDate);

        //sessionをString型に
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

        if ("出勤".equals(status)) {
            //  出勤時間をDBに追加
            String sql = "insert into work (login_id,date,start_work,work_condition_id) values (" + login_id + " ,'" + formatNowDate2 + "','" + formatNowDate + "',1)";
            String sql2 = "select work_condition_id from work where login_id = '" + login_id + "'  and date = '" + formatNowDate2 + "' ";
            List<Work> works = jdbcTemplate.query(sql2, new DataClassRowMapper<>(Work.class));
            if (works.isEmpty() || works.get(0).getWorkCondition() == "") {
                jdbcTemplate.update(sql);
            }else{
                model.addAttribute("error", "既に出勤は入力されています");
            }
            System.out.println(status);

        } else if ("退勤".equals(status)) {
            String sql2 = "select end_work from work where login_id = '" + login_id + "'  and date = '" + formatNowDate2 + "' ";
            List<Work> works = jdbcTemplate.query(sql2, new DataClassRowMapper<>(Work.class));
            if (works.get(0).getEndWork() == null) {
                String sql = "update work set end_work = '" + formatNowDate + "', work_condition_id = '4' where login_id = '" + login_id + "' and date = '" + formatNowDate2 + "'";
                jdbcTemplate.update(sql);
            } else {
                model.addAttribute("error", "既に退勤は入力されています");
            }
            System.out.println(status);

        } else if ("休憩開始".equals(status)) {
            String sql2 = "select start_break from work where login_id = '" + login_id + "'  and date = '" + formatNowDate2 + "' ";
            List<Work> works = jdbcTemplate.query(sql2, new DataClassRowMapper<>(Work.class));
            if (works.get(0).getStartBreak() == null) {
                String sql = "update work set start_break = '" + formatNowDate + "', work_condition_id = '3' where login_id = '" + login_id + "' and date = '" + formatNowDate2 + "'";
                jdbcTemplate.update(sql);
            } else {
                model.addAttribute("error", "既に休憩開始は入力されています");
            }
            System.out.println(status);

        } else if ("休憩終了".equals(status)) {
            String sql2 = "select end_break from work where login_id = '" + login_id + "'  and date = '" + formatNowDate2 + "' ";
            List<Work> works = jdbcTemplate.query(sql2, new DataClassRowMapper<>(Work.class));
            if (works.get(0).getEndBreak() == null) {
                String sql = "update work set end_break = '" + formatNowDate + "', work_condition_id = '1' where login_id = '" + login_id + "' and date = '" + formatNowDate2 + "'";
                jdbcTemplate.update(sql);
            } else {
                model.addAttribute("error", "既に休憩終了は入力されています");
            }
            System.out.println(status);
        }

        //出勤状態の取得
        String getWorkConditonSql = "select work_condition_id from work where login_id = '" + login_id + "' and date = '" + formatNowDate2 + "'";
        List<Map<String, Object>> workCondition = jdbcTemplate.queryForList(getWorkConditonSql);
        if (workCondition.isEmpty()){
            model.addAttribute("availability", "none");
        }else{
            Map<String, Object> getWorConditon = workCondition.get(0);
            String workConditionId = (String) getWorConditon.get("work_condition_id");
            model.addAttribute("availability", workConditionId);
        }

        return "work";
    }
}