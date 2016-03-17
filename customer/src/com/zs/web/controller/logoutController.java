package com.zs.web.controller;

import com.zs.domain.basic.User;
import com.zs.service.basic.user.ValidateLoginService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Allen on 2015/5/18.
 */
@Controller
@RequestMapping(value = "/logoutUser")
public class logoutController extends LoggerController<User, ValidateLoginService>{
    private static Logger log = Logger.getLogger(logoutController.class);

    @RequestMapping(value = "logou")
    public String logou(HttpServletRequest request, HttpServletResponse response){
        try{
            request.getSession().removeAttribute("loginName");
            request.getSession().removeAttribute("name");
            request.getSession().removeAttribute("loginType");
            request.getSession().removeAttribute("menuMap");
        }catch(Exception e){
            super.outputException(request, e, log, "用户登出");
            return "error";
        }
        return "../../login";
    }
}
