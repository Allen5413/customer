package com.zs.web.controller.basic.usergroup;

import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupService;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 通过ID查询用户组信息
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/findUserGroup")
public class FindUserGroupByIdController extends
        LoggerController<UserGroup, FindUserGroupService> {

    private static Logger log = Logger.getLogger(FindUserGroupByIdController.class);

    @Resource
    private FindUserGroupService findUserGroupService;

    @RequestMapping(value = "findUserGroupByID")
    public String findUserGroupByID(@RequestParam("id") long id, HttpServletRequest request){
        try {
            UserGroup userGroup = findUserGroupService.get(id);
            request.setAttribute("userGroup", userGroup);
            return "user/userAdd";
        }catch(Exception e){
            super.outputException(request, e, log, "查询用户组详情");
            return "error";
        }
    }
}
