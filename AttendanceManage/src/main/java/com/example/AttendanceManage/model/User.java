package com.example.AttendanceManage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String password;
    private String name;
    private String role;
    private int division_id;
}
