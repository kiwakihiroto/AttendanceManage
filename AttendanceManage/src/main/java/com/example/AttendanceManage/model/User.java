package com.example.AttendanceManage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    // データベースのカラム名に合わせる
    private int user_id;
    private int login_id;
    private String user_name;
    private String password;
    private String tel;
    private String mail;
    private String remarks;
    private String department_id;
    private String admin_id;
}
