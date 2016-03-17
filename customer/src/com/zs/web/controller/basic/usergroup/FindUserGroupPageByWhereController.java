package com.zs.web.controller.basic.usergroup;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupPageByWhereService;
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
@RequestMapping(value = "/findUserGroupPage")
public class FindUserGroupPageByWhereController extends
        LoggerController<UserGroup, FindUserGroupPageByWhereService> {

    private static Logger log = Logger.getLogger(FindUserGroupPageByWhereController.class);

    @Resource
    public FindUserGroupPageByWhereService findUserGroupPageByWhereService;

    @RequestMapping(value = "findUserGroupPageByWhere")
    public String FindPageByWhere(@RequestParam(value="name", required=false, defaultValue="") String name,
                                  HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name.trim());
            PageInfo<UserGroup> pageInfo = getPageInfo(request);
            Map<String, Boolean> sortMap = getSortMap(request);
            sortMap.put("operateTime", false);
            pageInfo = findUserGroupPageByWhereService.findPageByWhere(pageInfo, params, sortMap);
            request.setAttribute("pageInfo", pageInfo);
        } catch (Exception e) {
            super.outputException(request, e, log, "分页查询用户组");
            return "error";
        }
        return "userGroup/userGroupList";
    }
}


