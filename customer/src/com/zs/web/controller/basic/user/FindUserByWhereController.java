package com.zs.web.controller.basic.user;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.user.FindUserPageByWhereService;
import com.zs.service.basic.usergroup.FindUserGroupForParentSignByZzCodeService;
import com.zs.service.basic.usergroup.FindUserGroupService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页查询用户组信息
 * Created by Allen on 2015/4/27.
 */
@Controller
@RequestMapping(value = "/findUserByWhere")
public class FindUserByWhereController extends
        LoggerController<User, FindUserPageByWhereService> {

    private static Logger log = Logger.getLogger(FindUserByWhereController.class);

    @Resource
    private FindUserPageByWhereService findUserPageByWhereService;
    @Resource
    private FindUserGroupService findUserGroupService;
    @Resource
    private FindUserGroupForParentSignByZzCodeService findUserGroupForParentSignByZzCodeService;

    @RequestMapping(value = "find")
    public String FindPageByWhere(@RequestParam(value="s_name", required=false, defaultValue="") String name,
                                  @RequestParam(value="s_zzCode", required=false, defaultValue="") String zzCode,
                                  @RequestParam(value="s_state", required=false, defaultValue="") String state,
                                  @RequestParam(value="s_groupId", required=false, defaultValue="") String groupId,
                                  @RequestParam(value="s_level", required=false, defaultValue="") String level,
                                  HttpServletRequest request) {
        try {
            int loginUserLevel = UserTools.getLoginUserForLevel(request);
            String loginUserZzCode = UserTools.getLoginUserForZzCode(request);
            //如果是公司级别的就查询所有的角色，不是就查询自己创建的角色
            List<UserGroup> userGroupList = null;
            if(loginUserLevel == User.LEVEL_COMPANY){
                userGroupList = findUserGroupService.getAll();
            }else{
                userGroupList = findUserGroupForParentSignByZzCodeService.find(loginUserZzCode);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name.trim());
            params.put("zzCode", zzCode.trim());
            params.put("state", state.trim());
            params.put("groupId", groupId.trim());
            params.put("level", level.trim());
            params.put("loginUserLevel", loginUserLevel+"");
            params.put("loginUserZzCode", loginUserZzCode);
            PageInfo pageInfo = getPageInfo(request);
            pageInfo = findUserPageByWhereService.findPageByWhere(pageInfo, params);
            request.setAttribute("pageInfo", pageInfo);
            request.setAttribute("userGroupList", userGroupList);
        } catch (Exception e) {
            super.outputException(request, e, log, "分页查询用户");
            return "error";
        }
        return "user/userList";
    }
}


