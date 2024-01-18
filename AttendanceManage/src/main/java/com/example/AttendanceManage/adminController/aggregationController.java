package com.example.AttendanceManage.adminController;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.AttendanceManage.repository.UserRepository;
import com.example.AttendanceManage.repository.WorkRepository;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Controller
public class aggregationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpSession session;
    @Autowired
    public aggregationController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;

    @GetMapping("/admin/aggregation")
    public String GetAggregation(Model model){
        List<Integer> loginIdList = userRepository.findLoginIdBy();
        //ワイルドカードyyyy-MM-%
        DateTimeFormatter sqlDtf = DateTimeFormatter.ofPattern("yyyy-MM");
        String nowString = LocalDateTime.now().format(sqlDtf)+"-%";
        System.out.println(nowString);
        //例
        nowString = "2023-12-%";

        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<String> timeList = new ArrayList<>();
        //timeList.get()を分解 date,start_work,end_work,start_break,end_break
        String[] timeListList = new String[5];
        //timeListListの[]
        final int date = 0;
        final int start_work = 1;
        final int end_work = 2;
        final int start_break = 3;
        final int end_break = 4;

        long total = 0;
        Duration duration;

        //累計時間をいれる
        List<String> totalTimeList = new ArrayList<>();
        //htmlに送る用
        HashMap<String,String> totalTimeHashMap = new HashMap<>();

        for(int i=0;i<loginIdList.size();i++){
            System.out.println("loginId = "+loginIdList.get(i));
            timeList = workRepository.findDateByLoginIdAndDate(loginIdList.get(i),nowString);
            System.out.println(timeList);
            System.out.println(timeList.isEmpty());
            if(!timeList.isEmpty()) {
                System.out.println("timeList回数 = "+timeList.size());
                for(int j=0;j<timeList.size();j++){
                    System.out.println(j+" "+timeList.get(j));
                    //timeList.get()を分解
                    timeListList = timeList.get(j).split(",");

                    //work
                    System.out.println("nullCheck endWork = "+timeListList[end_work]);
                    //nullチェック
                    if(Objects.equals(timeListList[end_work], "null")){
                        System.out.println("null");
                        if(j == timeList.size()-1){
                            timeListList[end_work] = LocalDateTime.now().format(tf);
                        }else {
                            timeListList[end_work] = timeListList[start_work];
                        }
                    }
                    //通常か夜勤か
                    System.out.println(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_work],dtf)+" "+LocalDateTime.parse(timeListList[date]+" "+timeListList[end_work],dtf));
                    if(!LocalDateTime.parse(timeListList[date]+" "+timeListList[start_work],dtf).isAfter(LocalDateTime.parse(timeListList[date]+" "+timeListList[end_work],dtf))){
                        System.out.println(true);
                        System.out.println(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_work],dtf)+" "+LocalDateTime.parse(timeListList[date]+" "+timeListList[end_work],dtf));
                        duration = Duration.between(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_work],dtf),LocalDateTime.parse(timeListList[date]+" "+timeListList[end_work],dtf));
                    }else {
                        System.out.println(false);
                        System.out.println(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_work],dtf)+" "+LocalDateTime.parse(LocalDate.parse(timeListList[date]).plusDays(1)+" "+timeListList[end_work],dtf));
                        duration = Duration.between(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_work],dtf),LocalDateTime.parse(LocalDate.parse(timeListList[date]).plusDays(1)+" "+timeListList[end_work],dtf));
                    }
                    System.out.println("duration = "+duration.toHours()+":"+duration.toMinutesPart()+":"+duration.toSecondsPart());
                    System.out.println("minutes = "+duration.toSeconds());
                    total += duration.toSeconds();



                    //break
                    if(!(Objects.equals(timeListList[start_break], "null")) && !(Objects.equals(timeListList[end_break], "null"))){
                        if(!LocalDateTime.parse(timeListList[date]+" "+timeListList[start_break],dtf).isAfter(LocalDateTime.parse(timeListList[date]+" "+timeListList[end_break],dtf))){
                            duration = Duration.between(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_break],dtf),LocalDateTime.parse(timeListList[date]+" "+timeListList[end_break],dtf));
                        }else {
                            duration = Duration.between(LocalDateTime.parse(timeListList[date]+" "+timeListList[start_break],dtf),LocalDateTime.parse(LocalDate.parse(timeListList[date]).plusDays(1)+" "+timeListList[end_break],dtf));
                        }
                        System.out.println(duration.toSeconds());
                        total -= duration.toSeconds();
                        System.out.println(total);
                    }


                    for (int count=0;count<5;count++)
                        System.out.println(timeListList[count]);
                }
            }
            System.out.println("total =  "+total);
            duration = Duration.ofSeconds(total);
            System.out.println("total時 = "+ duration.toHours()+":"+duration.toMinutesPart()+":"+duration.toSecondsPart());
            totalTimeList.add(duration.toHours()+":"+duration.toMinutesPart());
            System.out.println("------------------------------------------------------------");

            total = 0;


        }
        for(int i=0;i<loginIdList.size();i++)
            totalTimeHashMap.put(userRepository.findUserNameByLoginId(loginIdList.get(i)), totalTimeList.get(i));

        System.out.println(totalTimeList);
        model.addAttribute("totalTime",totalTimeHashMap);

        return "/admin/aggregation";

    }

    @PostMapping("/admin/aggregation")
    public String postAggregation(Model model){

        String login_id = (String) this.session.getAttribute("login_id");

        return "/admin/aggregation";
    }
}
