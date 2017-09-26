package com.zs.web.Interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证拦截器
 * Created by Allen on 2015/4/26.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    //不需要拦截的路径
    private static final String[] IGNORE_URI = {"/login", "/css", "/bankNotify", "/attop"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            String zzCode = null == request.getSession().getAttribute("zzCode") ? "" : request.getSession().getAttribute("zzCode").toString();
            String name = null == request.getSession().getAttribute("name") ? "" : request.getSession().getAttribute("name").toString();
            if (!StringUtils.isEmpty(zzCode) && !StringUtils.isEmpty(name)) {
                flag = true;
            }else{
                if(url.toLowerCase().indexOf("app") > -1){
                    response.sendRedirect("/cust/loginUser/appLogin.htm");
                }else {
                    response.sendRedirect("/cust/login.jsp");
                }
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
