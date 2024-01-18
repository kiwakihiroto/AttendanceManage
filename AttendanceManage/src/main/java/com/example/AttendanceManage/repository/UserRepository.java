package com.example.AttendanceManage.repository;

import com.example.AttendanceManage.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM attendances WHERE login_id = :loginId",nativeQuery = true)
    User findByLoginId(@Param("loginId") Integer loginId);

    //tel,mail,remarksの更新
    @Modifying
    @Query(value = "UPDATE attendances SET tel = :tel,mail = :mail,remarks = :remarks WHERE login_id = :loginId",nativeQuery = true)
    void updateAddressByLoginId(@Param("tel") String tel,@Param("mail") String mail,@Param("remarks") String remarks,@Param("loginId") Integer loginId);

    @Query(value = "SELECT login_id from attendances ORDER BY login_id ASC",nativeQuery = true)
    List<Integer> findLoginIdBy();

    @Query(value = "SELECT user_name FROM attendances WHERE login_id = :loginId ORDER BY login_id ASC",nativeQuery = true)
    String findUserNameByLoginId(@Param("loginId") Integer loginId);


}


