package com.example.AttendanceManage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private com.example.AttendanceManage.login.loginUserService loginUserService;

    public Boolean isAdmin(String loginId){

        String admin_id = loginUserService.getAdmin(loginId);
        if("2".equals(admin_id)){
            return true;
        }
        return false;
    }

}
