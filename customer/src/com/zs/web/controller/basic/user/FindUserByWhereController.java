package com.zs.web.controller.basic.user;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.user.FindUserPageByWhereService;
import com.zs.service.basic.usergroup.FindUserGroupPageByWhereService;
import com.zs.service.basic.usergroup.FindUserGroupService;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @RequestMapping(value = "find")
    public String FindPageByWhere(@RequestParam(value="name", required=false, defaultValue="") String name,
                                  @RequestParam(value="zzCode", required=false, defaultValue="") String zzCode,
                                  @RequestParam(value="state", required=false, defaultValue="") String state,
                                  @RequestParam(value="groupId", required=false, defaultValue="") String groupId,
                                  HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name.trim());
            params.put("zzCode", zzCode.trim());
            params.put("state", state.trim());
            params.put("groupId", groupId.trim());
            PageInfo pageInfo = getPageInfo(request);
            pageInfo = findUserPageByWhereService.findPageByWhere(pageInfo, params);
            request.setAttribute("pageInfo", pageInfo);
            request.setAttribute("userGroupList", findUserGroupService.getAll());
        } catch (Exception e) {
            super.outputException(request, e, log, "分页查询用户");
            return "error";
        }
        return "user/userList";
    }
}


