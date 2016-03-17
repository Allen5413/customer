package com.zs.web.controller.basic.user;

import com.zs.domain.basic.User;
import com.zs.service.basic.user.DelUserService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 删除用户组
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/delUser")
public class DelUserController extends
        LoggerController<User, DelUserService> {
    private static Logger log = Logger.getLogger(DelUserController.class);

    @Resource
    private DelUserService delUserService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("ids") String ids, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try{
            delUserService.del(UserTools.getLoginUserForZzCode(request), ids.split(","));
            jsonObject.put("state", 0);
        } catch (Exception e){
            String msg = super.outputException(request, e, log, "删除用户");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
