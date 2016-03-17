package com.zs.web.controller.basic.usergroupuser;

import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.usergroupuser.AddUserGroupUserService;
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
 * Created by Allen on 2015/5/21.
 */
@Controller
@RequestMapping(value = "/addUserGroupUser")
public class AddUserGroupUserController extends
        LoggerController<UserGroupUser, AddUserGroupUserService> {
    private static Logger log = Logger.getLogger(AddUserGroupUserController.class);

    @Resource
    private AddUserGroupUserService addUserGroupUserService;

    @RequestMapping(value = "userGroupUserAdd")
    @ResponseBody
    public JSONObject addUserGroupUser(@RequestParam(value="loginNames") String loginNames,
                                       @RequestParam(value="userGroupIds", required=false, defaultValue="") String userGroupIds,
                                       @RequestParam(value="type") int type,
                                       HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try{
            addUserGroupUserService.addUserGroupUser(loginNames, userGroupIds, type, UserTools.getLoginUserForName(request));
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "新增用户组与用户关联");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
