package com.example.AttendanceManage.controller;

import com.example.AttendanceManage.login.loginUserService;
import com.example.AttendanceManage.model.User;
import com.example.AttendanceManage.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class addressController {
    @Autowired
    UserRepository userRepository;

    HttpSession session;

    @Autowired
    private com.example.AttendanceManage.repository.AdminRepository AdminRepository;

    @Autowired
    public addressController(HttpSession session){
        this.session = session;
    }

    @GetMapping("/address")
    public String addressGet(Model model, ModelAndView mav) {
        //元のデータをaddress.htmlに渡す
        int loginId = Integer.parseInt(session.getAttribute("login_id").toString());
        User user = userRepository.findByLoginId(loginId);
        model.addAttribute("user",user);

        //admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(Integer.toString(loginId)));

        return "address";
    }

    @PostMapping("/address")
    public String addressPost(Model model,HttpServletRequest request) {
        int loginId = Integer.parseInt(session.getAttribute("login_id").toString());

        //admin取得
        model.addAttribute("isAdmin", AdminRepository.isAdmin(Integer.toString(loginId)));

        String tell = request.getParameter("tell");
        String mail = request.getParameter("email");
        String remarks = request.getParameter("remarks");
        User user = userRepository.findByLoginId(loginId);
        String tellFormat = "";
        StringBuilder tellSB = new StringBuilder();
        final int TELLLENGTH = 13;
        final int TELLLENGTHNOTHYPHEN = 11;
        final int TELLHYPHEN3 = 3;
        final int TELLHYPHEN8 = 8;
        final int REMARKSIZE = 128;


        //tell
        if(Objects.equals(tell, "")) {
            //何も入力がない場合元のデータを入れる
            tellFormat = user.getTel();
        }else if(tell.length() == TELLLENGTH && tell.charAt(TELLHYPHEN3) == '-' && tell.charAt(TELLHYPHEN8) == '-') {
            //ハイフン込み
            tellFormat = tell;
        }else if(tell.length() == TELLLENGTHNOTHYPHEN && !tell.matches("-")) {
            //ハイフンなし
            //3個目と8個目の間にハイフンを入れる
            tellFormat = tellSB.append(tell).insert(TELLHYPHEN3,"-").insert(TELLHYPHEN8,"-").toString();
            System.out.println(tellFormat);
        }else {
            //長さが合わないとき
            System.out.println("error:address:tel|telの長さが合いません");
            model.addAttribute("user",user);
            model.addAttribute("errorAddress", "正しくありません");
            return "address";
        }

        //mail
        if(Objects.equals(mail,"")) {
            //未入力の場合元のデータを入れる
            mail = user.getMail();
        }else if(!mail.matches(".*@.*")){
            //@がない場合
            System.out.println("error:address:mail|@がありません");
            model.addAttribute("user",user);
            model.addAttribute("errorAddress", "正しくありません");
            return "address";
        }else if(mail.matches(".*@") || mail.matches("@.*")){
            //@の前後が足りない場合
            System.out.println("error:address:mail|@の前後が正しくありません");
            model.addAttribute("user",user);
            model.addAttribute("errorAddress", "正しくありません");
            return "address";
        }

        //remark
        if (Objects.equals(remarks,"")) {
            //未入力の場合元のデータを入れる
            remarks = user.getRemarks();
        }else if(remarks.length() >= REMARKSIZE){
            //128文字以上の場合
            System.out.println("error:address:remarks|入力が長すぎます");
            model.addAttribute("user",user);
            model.addAttribute("errorAddress", "備考欄の入力が長すぎます。128文字に収めてください");
            return "address";
        }else {
            //データベースに入れる際改行して入るため
            remarks = remarks.replace("\n","");
        }
        //データの更新
        //System.out.println("登録先: login_id = "+loginId+"\n登録内容: tell = "+tellFormat+" mail = "+mail+" remarks = "+remarks);
        System.out.println("登録先: login_id = "+loginId+"\n登録内容: tell = "+tellFormat+" mail = "+mail);
        System.out.println(" remarks = "+remarks.replace("\r",""));
        userRepository.updateAddressByLoginId(tellFormat,mail,remarks,loginId);
        System.out.println("address登録完了");

        //userに変更内容を入れる
        user = userRepository.findByLoginId(loginId);
        //address.htmlに変更後のデータを渡す
        model.addAttribute("user",user);
        return "address";
    }
}
