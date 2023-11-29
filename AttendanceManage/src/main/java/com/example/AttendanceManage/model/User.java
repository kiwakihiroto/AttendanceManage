package com.example.AttendanceManage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int login_id;
    private String user_name;
    private String password;
    private String department_id;
    private int admin_id;
}
