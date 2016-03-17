package com.zs.web.controller.basic.usergroup;

import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.DelUserGroupService;
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
@RequestMapping(value = "/delUserGroup")
public class DelUserGroupController extends
        LoggerController<UserGroup, DelUserGroupService> {
    private static Logger log = Logger.getLogger(DelUserGroupController.class);

    @Resource
    private DelUserGroupService delUserGroupService;

    @RequestMapping(value = "userGroupDel")
    @ResponseBody
    public JSONObject delUserGroup(@RequestParam("ids") String ids, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try{
            delUserGroupService.delUserGroup(ids.split(","));
            jsonObject.put("state", 0);
        } catch (Exception e){
            String msg = super.outputException(request, e, log, "删除角色");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
