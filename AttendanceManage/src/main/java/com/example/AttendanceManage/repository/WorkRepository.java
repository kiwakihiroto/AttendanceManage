package com.example.AttendanceManage.repository;

import com.example.AttendanceManage.model.Work;
import com.example.AttendanceManage.model.WorkKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
@Transactional
public interface WorkRepository extends JpaRepository<Work, WorkKey> {
    @Query(value = "SELECT count(*) FROM work WHERE work.login_id = :login_id AND work.date = :date",nativeQuery = true)
    int countWorkByLoginIdAndDate(@Param("login_id") Integer login_id, @Param("date") Date date);
/*
    @Modifying
    @Query(value = "update work set work.work_place_id = :work_place_id where work.login_id = :login_id and work.date = :date and work.end_work is null")
    void update(@Param("work_place_id") String work_place_id,@Param("login_id") Integer login_id,@Param("date") Date date);
 */
}
