package com.qhj.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by QHJ on 2021/3/25
 * 登录拦截器
 */
//@Component
public class LoginInterceptor implements HandlerInterceptor {

//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getSession().getAttribute("user") == null) {
//            response.sendRedirect("/admin/login");
//            return false;
//        }
//        return true;
//    }

}
