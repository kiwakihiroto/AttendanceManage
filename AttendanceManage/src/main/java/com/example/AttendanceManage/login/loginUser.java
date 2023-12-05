package com.example.AttendanceManage.login;

import com.example.AttendanceManage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class loginUser {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<User> SelectLoginUser(User input_user) {

        int id = input_user.getLogin_id();
        String pw = input_user.getPassword();
        List<User> array_userinfo = new ArrayList<>();

        try {

            String sql = "select * from attendances where login_id = ? and password = ?";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id, pw);

            for (Map<String, Object> row : rows) {
                User resultUserInfo = new User();
                resultUserInfo.setUser_id((Integer) row.get("user_id"));
                resultUserInfo.setLogin_id((Integer) row.get("login_id"));
                resultUserInfo.setUser_name((String) row.get("user_name"));
                resultUserInfo.setPassword((String) row.get("password"));
                resultUserInfo.setTel((String) row.get("tel"));
                resultUserInfo.setMail((String) row.get("mail"));
                resultUserInfo.setRemarks((String) row.get("remarks"));
                resultUserInfo.setDepartment_id((String) row.get("department_id"));
                resultUserInfo.setAdmin_id((String) row.get("admin_id"));
                array_userinfo.add(resultUserInfo);
            }

            System.out.println(array_userinfo);

        }catch (Exception e){
            System.out.println("sql実行失敗: " + e.getMessage());
            e.printStackTrace();
        }

        return array_userinfo;
    }

}