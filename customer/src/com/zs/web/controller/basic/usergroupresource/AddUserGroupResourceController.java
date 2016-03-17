package com.zs.web.controller.basic.usergroupresource;

import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.usergroupresource.AddUserGroupResourceService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 新增用户组组关联资源
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/addUserGroupResource")
public class AddUserGroupResourceController extends
        LoggerController<UserGroupResource, AddUserGroupResourceService> {

    private static Logger log = Logger.getLogger(AddUserGroupResourceController.class);

    @Resource
    private AddUserGroupResourceService addUserGroupResourceService;


    /**
     * 新增用户组关联资源
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request,
                      @RequestParam("groupId") long groupId,
                      @RequestParam(value = "isBrowse", required = false, defaultValue = "") String isBrowse,
                      @RequestParam(value = "isAdmin", required = false, defaultValue = "") String isAdmin,
                      @RequestParam(value = "isAssign", required = false, defaultValue = "") String isAssign,
                      @RequestParam(value = "rIds", required = false, defaultValue = "") String rIds){
        JSONObject jsonObject = new JSONObject();
        try{
            addUserGroupResourceService.add(groupId, isBrowse, isAdmin, isAssign, UserTools.getLoginUserForZzCode(request), StringUtils.isEmpty(rIds) ? null : rIds.split(","));
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "保存角色权限");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
