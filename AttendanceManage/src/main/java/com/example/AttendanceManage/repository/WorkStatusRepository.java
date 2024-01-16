package com.example.AttendanceManage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WorkStatusRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getName(String loginId){
        String getUserNameSql = "select user_name from attendances where login_id = '" + loginId +"'";
        //名前の取得
        List<Map<String, Object>> name = jdbcTemplate.queryForList(getUserNameSql);
        //list[0]の値を取得
        Map<String, Object> getUserName = name.get(0);
        //user_nameを指定して値の取得
        String userName = (String) getUserName.get("user_name");

        return userName;
    }

    public String getCondition(String loginId, String date){
        String sql = "select work_condition_id from work where login_id = '" + loginId + "' and date = '" + date + "'";
        return sql;
    }
    public String insretWorkData(String loginId,String date,String time){
        String sql = "insert into work (login_id,date,start_work,work_condition_id) values (" + loginId + " ,'" + date + "','" + time + "',1)";
        return sql;
    }

    public String getWorkConditionId(String loginId, String date){
        String sql = "select work_condition_id from work where login_id = '" + loginId + "'  and date = '" + date + "' ";
        return sql;
    }

    public String getEndWork(String loginId,String date){
        String sql = "select end_work from work where login_id = '" + loginId + "'  and date = '" + date + "' ";
        return sql;
    }

    public String updateEndWork(String loginId,String date, String time){
        String sql = "update work set end_work = '" + time + "', work_condition_id = '4' where login_id = '" + loginId + "' and date = '" + date + "'";
        return sql;
    }

    public String getStartBreak(String loginId, String date){
        String sql = "select start_break from work where login_id = '" + loginId + "'  and date = '" + date + "' ";
        return  sql;
    }

    public String updateStartBreak(String loginId, String date,String time){
        String sql = "update work set start_break = '" + time + "', work_condition_id = '3' where login_id = '" + loginId + "' and date = '" + date + "'";
        return sql;
    }

    public String getEndBreak(String loginId, String date){
        String sql = "select  from work where login_id = '" + loginId + "'  and date = '" + date + "' ";
        return  sql;
    }

    public String updateEndBreak(String loginId, String date,String time){
        String sql = "update work set end_break = '" + time + "', work_condition_id = '1' where login_id = '" + loginId + "' and date = '" + date + "'";
        return sql;
    }
}
