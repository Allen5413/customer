package com.zs.web.controller.basic.usergroupresource;

import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupPageByWhereService;
import com.zs.service.basic.usergroup.FindUserGroupService;
import com.zs.service.basic.usergroupresource.FindUserGroupResourceByGroupIdService;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Allen on 2016/3/10.
 */
@Controller
@RequestMapping(value = "/findUserGroupResource")
public class FindUserGroupResourceController extends
        LoggerController<UserGroup, FindUserGroupPageByWhereService> {

    private static Logger log = Logger.getLogger(FindUserGroupResourceController.class);

    @Resource
    private FindUserGroupService findUserGroupService;
    @Resource
    private FindUserGroupResourceByGroupIdService findUserGroupResourceByGroupIdService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="groupId", required=false, defaultValue="") String groupId,
                                  HttpServletRequest request) {
        try {
            //查询所有角色
            List<UserGroup> userGroupList = findUserGroupService.getAll();
            if(StringUtils.isEmpty(groupId)){
                groupId = userGroupList.get(0).getId()+"";
            }
            //查询角色关联资源
            List<JSONObject> resourceList = findUserGroupResourceByGroupIdService.find(groupId);
            //查询角色信息
            UserGroup userGroup = findUserGroupService.get(Long.parseLong(groupId));

            request.setAttribute("userGroupList", userGroupList);
            request.setAttribute("resourceList", resourceList);
            request.setAttribute("userGroup", userGroup);

        } catch (Exception e) {
            super.outputException(request, e, log, "查询角色使用权限");
            return "error";
        }
        return "userGroupResource/list";
    }
}
