package com.zs.web.controller.basic.user;

import com.zs.domain.basic.User;
import com.zs.service.basic.user.AddUserService;
import com.zs.service.basic.usergroup.FindUserGroupService;
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
 * 新增用户
 * Created by Allen on 2015/4/27.
 */
@Controller
@RequestMapping(value = "/addUser")
public class AddUserController extends
        LoggerController<User, AddUserService> {
    private static Logger log = Logger.getLogger(AddUserController.class);

    @Resource
    private AddUserService addUserService;
    @Resource
    private FindUserGroupService findUserGroupService;

    /**
     * 打开新增用户页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        request.setAttribute("userGroupList", findUserGroupService.getAll());
        return "user/userAdd";
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, User user,
                          @RequestParam("userGroupId")long userGroupId){
        JSONObject jsonObject = new JSONObject();
        try{
            if(null != user) {
                addUserService.addUser(user, userGroupId, UserTools.getLoginUserForZzCode(request));
            }
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "新增用户");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
