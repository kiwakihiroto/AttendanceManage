package com.example.AttendanceManage.Interceptors;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionCheckInterceptor implements HandlerInterceptor {

    //controllerの前に実行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //sessionにlogin_idがない場合login.htmlにとばす
        if(request.getSession().getAttribute("login_id") == null) { // 必要なセッション情報を確認
            response.sendRedirect("login");
            return false;
        }

        return true;
    }

}
