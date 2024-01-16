package com.example.AttendanceManage.controller;
import com.example.AttendanceManage.login.loginUserService;
import com.example.AttendanceManage.model.Work;
import com.example.AttendanceManage.repository.WorkStatusRepository;
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
    private com.example.AttendanceManage.repository.WorkStatusRepository WorkStatusRepository;
    @Autowired
    private com.example.AttendanceManage.repository.AdminRepository AdminRepository;


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

        //admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(login_id));

        //名前の表示
        model.addAttribute("userName", WorkStatusRepository.getName(login_id));

        //日付取得
        Date nowDate = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatnowDate = sdf2.format(nowDate);

        //出勤状態の取得
        List<Map<String, Object>> workCondition = jdbcTemplate.queryForList(WorkStatusRepository.getCondition(login_id, formatnowDate));
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
        Date nowDate = new Date();

        //現在の時間を取得
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String formatNowTime = sdf1.format(nowDate);

        //日付取得
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatnowDate = sdf2.format(nowDate);

        //sessionをString型に
        String login_id = (String) this.session.getAttribute("login_id");

        //名前の表示
        model.addAttribute("userName", WorkStatusRepository.getName(login_id));

        if ("出勤".equals(status)) {
            //  出勤時間をDBに追加
            List<Work> works = jdbcTemplate.query(WorkStatusRepository.getWorkConditionId(login_id,formatnowDate), new DataClassRowMapper<>(Work.class));
            if (works.isEmpty() || works.get(0).getWorkCondition() == "") {
                jdbcTemplate.update(WorkStatusRepository.insretWorkData(login_id,formatnowDate,formatNowTime));
            }else{
                model.addAttribute("error", "既に出勤は入力されています");
            }
            System.out.println(status);

        } else if ("退勤".equals(status)) {
            List<Work> works = jdbcTemplate.query(WorkStatusRepository.getEndWork(login_id,formatnowDate), new DataClassRowMapper<>(Work.class));
            if (works.get(0).getEndWork() == null) {
                jdbcTemplate.update(WorkStatusRepository.updateEndWork(login_id,formatnowDate,formatNowTime));
            } else {
                model.addAttribute("error", "既に退勤は入力されています");
            }
            System.out.println(status);

        } else if ("休憩開始".equals(status)) {
            List<Work> works = jdbcTemplate.query(WorkStatusRepository.getStartBreak(login_id,formatnowDate), new DataClassRowMapper<>(Work.class));
            if (works.get(0).getStartBreak() == null) {
                jdbcTemplate.update(WorkStatusRepository.updateStartBreak(login_id,formatnowDate,formatNowTime));
            } else {
                model.addAttribute("error", "既に休憩開始は入力されています");
            }
            System.out.println(status);

        } else if ("休憩終了".equals(status)) {
            List<Work> works = jdbcTemplate.query(WorkStatusRepository.getEndBreak(login_id,formatnowDate), new DataClassRowMapper<>(Work.class));
            if (works.get(0).getEndBreak() == null) {
                jdbcTemplate.update(WorkStatusRepository.updateEndBreak(login_id,formatnowDate,formatNowTime));
            } else {
                model.addAttribute("error", "既に休憩終了は入力されています");
            }
            System.out.println(status);
        }

        //出勤状態の取得
        List<Map<String, Object>> workCondition = jdbcTemplate.queryForList(WorkStatusRepository.getCondition(login_id, formatnowDate));
        if (workCondition.isEmpty()){
            model.addAttribute("availability", "none");
        }else{
            Map<String, Object> getWorConditon = workCondition.get(0);
            String workConditionId = (String) getWorConditon.get("work_condition_id");
            model.addAttribute("availability", workConditionId);
        }

        //admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(login_id));

        return "work";
    }
}