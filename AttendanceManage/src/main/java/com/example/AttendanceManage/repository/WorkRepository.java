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
import java.util.List;


@Repository
@Transactional
public interface WorkRepository extends JpaRepository<Work, WorkKey> {
    @Query(value = "SELECT date,end_work FROM work WHERE login_id = :loginId AND date IN(:date,:nowDayAgo) ORDER BY date ASC",nativeQuery = true)
    List<String> findByLoginIdAndDateAndDate(@Param("loginId") Integer loginId,@Param("date") Date date,@Param("nowDayAgo") Date  nowDayAgo);

    @Query(value = "SELECT date,start_work,end_work,start_break,end_break from work WHERE login_id = :loginId",nativeQuery = true)
    List<String> findByLoginId(@Param("loginId") Integer integer);

    //work_place_idの更新
    @Modifying
    @Query(value = "update work set work_place_id = :workPlaceId where login_id = :loginId and date IN(:date,:nowDayAgo) and end_work is null",nativeQuery = true)
    void updateWorkPlaceIdByLoginIdAndDateAndEndWorkIsNull(@Param("workPlaceId") String workPlaceId,@Param("loginId") Integer loginId,@Param("date") Date date,@Param("nowDayAgo") Date nowDayAgo);

    @Query(value = "SELECT date,start_work,end_work,start_break,end_break from work WHERE login_id = :loginId AND CAST(date as text) LIKE :date",nativeQuery = true)
    List<String> findDateByLoginIdAndDate(@Param("loginId") Integer loginId,@Param("date") String date);

}
