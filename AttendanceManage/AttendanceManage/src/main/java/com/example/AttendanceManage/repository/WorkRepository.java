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
    @Query(value = "SELECT count(*) FROM work WHERE work.login_id = :loginId AND work.date IN(:date,:nowDayAgo) AND end_work is null",nativeQuery = true)
    int countByLoginIdAndDate(@Param("loginId") Integer loginId, @Param("date") Date date,@Param("nowDayAgo") Date nowDayAgo);

    //work_place_idの更新
    @Modifying
    @Query(value = "update work set work_place_id = :workPlaceId where login_id = :loginId and date IN(:date,:nowDayAgo) and end_work is null",nativeQuery = true)
    void updateWorkPlaceIdByLoginIdAndDateAndEndWorkIsNull(@Param("workPlaceId") String workPlaceId,@Param("loginId") Integer loginId,@Param("date") Date date,@Param("nowDayAgo") Date nowDayAgo);

}
