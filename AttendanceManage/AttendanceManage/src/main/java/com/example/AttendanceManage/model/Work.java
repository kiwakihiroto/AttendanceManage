package com.example.AttendanceManage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "work")
@IdClass(WorkKey.class)
public class Work {
    @Id
    @Column(name = "login_id")
    private int loginId;

    @Id
    @Column(name = "date")
    private Date date;

    @Column(name = "start_work")
    private Time startWork;
    @Column(name = "end_work")
    private Time endWork;
    @Column(name = "start_break")
    private Time startBreak;
    @Column(name = "end_break")
    private Time endBreak;
    @Column(name = "work_place_id")
    private String workPlaceId;
    @Column(name = "work_condition")
    private String workCondition;

    public Work(){}
}
