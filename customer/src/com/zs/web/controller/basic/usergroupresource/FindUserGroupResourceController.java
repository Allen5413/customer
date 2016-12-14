package com.zs.web.controller.basic.usergroupresource;

import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupByLevelService;
import com.zs.service.basic.usergroup.FindUserGroupPageByWhereService;
import com.zs.service.basic.usergroup.FindUserGroupService;
import com.zs.service.basic.usergroupresource.FindUserGroupResourceByGroupIdService;
import com.zs.tools.UserTools;
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
    @Resource
    private FindUserGroupByLevelService findUserGroupByLevelService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="s_groupId", required=false, defaultValue="") String groupId,
                                  HttpServletRequest request) {
        try {
            int loginLevel = UserTools.getLoginUserForLevel(request);
            List<UserGroup> userGroupList = null;
            if(loginLevel == UserGroup.LEVEL_COMPANY){
                userGroupList = findUserGroupByLevelService.getAll();
            }else{
                userGroupList = findUserGroupByLevelService.find(loginLevel);
            }

            if(StringUtils.isEmpty(groupId) && null != userGroupList && 0 < userGroupList.size()){
                groupId = userGroupList.get(0).getId()+"";
            }
            if(!StringUtils.isEmpty(groupId)) {
                //查询角色关联资源
                List<JSONObject> resourceList = findUserGroupResourceByGroupIdService.find(groupId, UserTools.getLoginUserForId(request));
                //查询角色信息
                UserGroup userGroup = findUserGroupService.get(Long.parseLong(groupId));
                request.setAttribute("resourceList", resourceList);
                request.setAttribute("userGroup", userGroup);
            }

            request.setAttribute("userGroupList", userGroupList);

        } catch (Exception e) {
            super.outputException(request, e, log, "查询角色使用权限");
            return "error";
        }
        return "userGroupResource/list";
    }
}
