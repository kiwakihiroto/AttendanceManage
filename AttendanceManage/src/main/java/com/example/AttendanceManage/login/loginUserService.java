package com.example.AttendanceManage.login;

import com.example.AttendanceManage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class loginUserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<User> SelectLoginUser(User input_user) {

        int id = input_user.getLogin_id();
        String pw = input_user.getPassword();
        List<User> array_userinfo = new ArrayList<>();

        try {

            String sql = "select * from attendances where login_id = ? and password = ?";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id, pw);

            //リファクタリングをしたため変更
            for (Map<String, Object> row : rows) {
                User resultUserInfo = User.mapToUser(row);
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