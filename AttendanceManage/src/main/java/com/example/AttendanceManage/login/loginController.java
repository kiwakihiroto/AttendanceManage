package com.example.AttendanceManage.login;
import com.example.AttendanceManage.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
public class loginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private com.example.AttendanceManage.login.loginUserService loginUserService;

    // HttpSession型のフィールドを定義する
    private HttpSession session;

    // コンストラクタを作成し、@Autowiredアノテーションを付与する
    @Autowired
    public loginController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        System.out.println(this.session.getAttribute("login_id"));
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String post(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException{

        //id,passの取得
        String id = request.getParameter("login_id");
        String pass = request.getParameter("password");

        // Sessionへの保存
        this.session.setAttribute("login_id", id);
        System.out.println(this.session.getAttribute("login_id"));

        try{
            //Userクラスのインスタンス
            User input_user = new User();

            //Userクラスに入力したid,passをセット
            input_user.setLogin_id(Integer.parseInt(id));
            input_user.setPassword(pass);

            //リストに検索結果を格納
            List<User> user_list = loginUserService.SelectLoginUser(input_user);
            System.out.println(user_list);

            if(user_list == null || user_list.size() != 1){
                model.addAttribute("error", "ログインに失敗しました。");
                System.out.println("ログイン失敗");
                return "login";
            }else{
                System.out.println("ログイン成功");
                return "work";
            }
        }catch(Exception e){
            System.out.println("失敗");
            return "login";
        }

    }

}
