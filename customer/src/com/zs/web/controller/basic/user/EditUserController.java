package com.zs.web.controller.basic.user;

import com.alibaba.fastjson.JSONArray;
import com.zs.domain.basic.User;
import com.zs.service.basic.user.EditUserService;
import com.zs.service.basic.user.FindUserForTreeService;
import com.zs.service.basic.usergroup.FindUserGroupByLevelService;
import com.zs.service.basic.usergroupuser.FindUserGroupUserByUserIdService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 修改用户信息
 * Created by Allen on 2015/4/27.
 */
@Controller
@RequestMapping(value = "/editUser")
public class EditUserController extends LoggerController<User, EditUserService> {

    private static Logger log = Logger.getLogger(EditUserController.class);

    @Resource
    private EditUserService editUserService;
    @Resource
    private FindUserGroupByLevelService findUserGroupByLevelService;
    @Resource
    private FindUserGroupUserByUserIdService findUserGroupUserByUserIdService;
    @Resource
    private FindUserForTreeService findUserForTreeService;

    /**
     * 打开编辑用户页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request){
        try {
            int level = UserTools.getLoginUserForLevel(request);
            if (level == User.LEVEL_COMPANY) {
                request.setAttribute("userGroupList", findUserGroupByLevelService.getAll());
            } else {
                request.setAttribute("userGroupList", findUserGroupByLevelService.find(level));
            }
            request.setAttribute("level", level);

            User user = editUserService.get(id);
            request.setAttribute("user", user);
            request.setAttribute("userGroupId", findUserGroupUserByUserIdService.find(user.getId()).getUserGroupId());

            JSONArray jsonArray = findUserForTreeService.findForEditUser(id, UserTools.getLoginUserForId(request));
            request.setAttribute("userTree", jsonArray);

            request.setAttribute("isEditMe", id == UserTools.getLoginUserForId(request) ? true : false);

        }catch (Exception e){
            super.outputException(request, e, log, "打开编辑账号页面");
            return "error";
        }
        return "user/userEdit";
    }

    /**
     * 编辑用户
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, User user,
                               @RequestParam("userGroupId")long userGroupId){
        JSONObject jsonObject = new JSONObject();
        try{
            if(null != user) {
                editUserService.editUser(user, userGroupId, UserTools.getLoginUserForZzCode(request), UserTools.getLoginUserForId(request));
            }
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "编辑用户");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
