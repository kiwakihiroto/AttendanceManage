package com.example.AttendanceManage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ConditionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAttendanceData(String loginId, String date){
        String sql = "select a.user_name, w.start_work, w.end_work, c.work_condition, p.work_place, a.tel, a.mail from work w\n" +
                "left join condition c on w.work_condition_id = c.work_condition_id\n" +
                "left join place p on w.work_place_id = p.work_place_id\n" +
                "join attendances a on w.login_id = a.login_id\n" +
                "where a.department_id = (select department_id from attendances where login_id = '" + loginId + "')\n" +
                "and w.date = '" + date +"'";

        List<Map<String, Object>> attendanceData = jdbcTemplate.queryForList(sql);

        return attendanceData;
    }
}
