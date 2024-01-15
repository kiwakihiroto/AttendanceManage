//後日消す
package com.example.AttendanceManage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class sampleSession {

    private HttpSession session;

    // コンストラクタを作成し、@Autowiredアノテーションを付与する
    @Autowired
    public sampleSession(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/sample")
    public String work(Model model) {
        System.out.println(this.session.getAttribute("login_id"));
        return "sample";
    }

}
