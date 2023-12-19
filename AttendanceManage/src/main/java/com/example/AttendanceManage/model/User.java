package com.example.AttendanceManage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "attendances")
public class User {
    // データベースのカラム名に合わせる
    @Id
    private int user_id;
    private int login_id;
    private String user_name;
    private String password;
    private String tel;
    private String mail;
    private String remarks;
    private String department_id;
    private String admin_id;

    public static User mapToUser(Map<String, Object> row) {
        //Mapをそれぞれの型に変換
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
        return resultUserInfo;
    }
}
