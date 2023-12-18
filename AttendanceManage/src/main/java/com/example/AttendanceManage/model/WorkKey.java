package com.example.AttendanceManage.model;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Setter
public class WorkKey implements Serializable {
    @Column(name = "login_id")
    private int loginId;
    @Column(name = "date")
    private Date date;
}