package com.example.AttendanceManage.controller;

import com.example.AttendanceManage.model.User;
import com.example.AttendanceManage.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class addressController {
    @Autowired
    UserRepository userRepository;

    HttpSession session;
    @Autowired
    public addressController(HttpSession session){
        this.session = session;
    }

    @GetMapping("/address")
    public String addressGet(Model model) {
        //備考欄にデータを渡す
        int loginId = Integer.parseInt(session.getAttribute("login_id").toString());
        String userRemarks = userRepository.findByLoginId(loginId).getRemarks();
        model.addAttribute("remarks",userRemarks);

        return "address";
    }

    @PostMapping("/address")
    public String addressPost(Model model,HttpServletRequest request) {
        int loginId = Integer.parseInt(session.getAttribute("login_id").toString());
        String tell = request.getParameter("tell");
        String mail = request.getParameter("email");
        String remarks = request.getParameter("remarks");
        User userList = userRepository.findByLoginId(loginId);

        //未入力の場合元のデータを入れる
        if(Objects.equals(tell, ""))
            tell = userList.getTel();
        if(Objects.equals(mail,""))
            mail = userList.getMail();
        if (Objects.equals(remarks,userList.getRemarks()))
            remarks = userList.getRemarks();

        System.out.println("loginId = "+loginId+" tell = "+tell+" mail = "+mail+" remarks = "+remarks);
        //データを更新
        userRepository.updateTellMailRemarksSetAttendance(tell,mail,remarks,loginId);

        //備考欄にデータを渡す
        model.addAttribute("remarks",userList.getRemarks());
        return "address";
    }
}