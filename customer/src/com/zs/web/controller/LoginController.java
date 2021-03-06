package com.zs.web.controller;

import com.feinno.framework.common.exception.BusinessException;
import com.zs.dao.basic.usergroupresource.FindUserGroupResourceByGroupIdDAO;
import com.zs.domain.basic.Menu;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroup;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.menu.FindMenuService;
import com.zs.service.basic.resource.FindResourceService;
import com.zs.service.basic.user.ValidateLoginService;
import com.zs.service.basic.usergroup.FindUserGroupByUserIdService;
import com.zs.tools.CookieTools;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 登录Controller
 * Created by Allen on 2015/4/25.
 */
@Controller
@RequestMapping(value = "/loginUser")
public class LoginController extends LoggerController<User, ValidateLoginService> {
    private static Logger log = Logger.getLogger(LoginController.class);

    @Resource
    private ValidateLoginService validateLoginService;
    @Resource
    private FindUserGroupByUserIdService findUserGroupByUserIdService;
    @Resource
    private FindUserGroupResourceByGroupIdDAO findUserGroupResourceByGroupIdDAO;
    @Resource
    private FindResourceService findResourceService;
    @Resource
    private FindMenuService findMenuService;

    /**
     * 用户登录
     * @param request
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public JSONObject login(@RequestParam(value="zzCode") String zzCode,
                            @RequestParam(value="pwd") String pwd,
                            HttpServletRequest request){
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        try{
            msg = loginUser(request, zzCode, pwd);
        }catch(Exception e){
            msg = super.outputException(request, e, log, "用户登录");
        }finally {
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }

    /**
     * 用户登录
     * @param request
     * @return
     */
    @RequestMapping(value = "loginAppForRestful")
    @ResponseBody
    public JSONObject loginAppForRestful(@RequestParam(value="zzCode") String zzCode,
                            @RequestParam(value="pwd") String pwd,
                            HttpServletRequest request,
                            HttpServletResponse response){
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        try{
            msg = loginUser(request, zzCode, pwd);
            CookieTools.add(response, "loginName", zzCode, 60 * 60 * 24 * 365);
            CookieTools.add(response, "pwd", pwd, 60 * 60 * 24 * 365);
        }catch(Exception e){
            msg = super.outputException(request, e, log, "用户登录");
        }finally {
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }

    /**
     * 用户登录
     * @param request
     * @return
     */
    @RequestMapping(value = "loginApp")
    public String loginApp(@RequestParam(value="zzCode") String zzCode,
                            @RequestParam(value="pwd") String pwd,
                            HttpServletRequest request,
                            HttpServletResponse response){
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        try{
            msg = loginUser(request, zzCode, pwd);
            if("用户名密码错误".equals(msg)){
                throw new BusinessException("用户名密码错误");
            }
            response.sendRedirect("/cust/findCustomerByWhereForApp/find.htm");
            return "app/customerList";
        }catch(Exception e){
            msg = super.outputException(request, e, log, "用户登录");
            try {
                try {
                    request.getRequestDispatcher("/loginUser/appLogin.htm?msg="+msg).forward(request,response);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            jsonObject.put("msg", msg);
        }
        return "error";
    }

    /**
     * 跳转到App登录页面
     * @param request
     * @return
     */
    @RequestMapping(value = "appLogin")
    public String appLogin(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "delcookie", required = false, defaultValue = "0")String delcookie)throws Exception{
        Cookie cookie = CookieTools.getCookieByName(request, "loginName");
        Cookie cookie2 = CookieTools.getCookieByName(request, "pwd");
        if(null != cookie && null != cookie2){
            String loginName = cookie.getValue();
            String pwd = cookie2.getValue();
            if(!StringUtils.isEmpty(loginName) && !StringUtils.isEmpty(pwd)){
                if("用户名密码错误".equals(loginUser(request, loginName, pwd))){
                    throw new BusinessException("用户名密码错误");
                }
                response.sendRedirect("/cust/findCustomerByWhereForApp/find.htm");
            }
        }
        return "/app/login";
    }


    protected String loginUser(HttpServletRequest request, String zzCode, String pwd)throws Exception{
        User user = validateLoginService.validateLogin(zzCode, pwd);
        if(null != user && !StringUtils.isEmpty(user.getName())){
            if(user.getState() == User.STATE_DELETE){
                throw new BusinessException("该用户不存在");
            }
            if(user.getState() == User.STATE_DISABLE){
                throw new BusinessException("该用户已经停用，请联系管理员");
            }
            return setSession(request, user.getZzCode(), user.getName(), user.getId(), user.getLevel());
        }else {
            return "用户名密码错误";
        }
    }

    protected String setSession(HttpServletRequest request, String zzCode, String name, long userId, int level)throws Exception{
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("zzCode", zzCode);
        request.getSession().setAttribute("name", name);
        request.getSession().setAttribute("level", level);
        //得到用户拥有的菜单资源权限
        Map<String, List<com.zs.domain.basic.Resource>> menuMap = this.getUserMenu(request, userId);
        request.getSession().setAttribute("menuMap", menuMap);
        return "success";
    }

    protected Map<String, List<com.zs.domain.basic.Resource>> getUserMenu(HttpServletRequest request, long userId){
        Map<String, List<com.zs.domain.basic.Resource>> menuResourceMap = new HashMap<String, List<com.zs.domain.basic.Resource>>();
        //获取用户所关联的用户组
        List<UserGroup> userGroupList = findUserGroupByUserIdService.find(userId);
        if(null != userGroupList && 0 < userGroupList.size()) {
            for(UserGroup userGroup : userGroupList) {
                //得到用户组关联资源
                List<UserGroupResource> userGroupResourceSet = findUserGroupResourceByGroupIdDAO.getUserGroupResourceByGroupId(userGroup.getId());
                Iterator<UserGroupResource> userGroupResourceIterator = userGroupResourceSet.iterator();

                while (userGroupResourceIterator.hasNext()){
                    UserGroupResource userGroupResource = userGroupResourceIterator.next();
                    //如果资源是客户资料管理，就要单独控制几个权限
                    if(1 == userGroupResource.getResourceId()){
                        request.getSession().setAttribute("isBrowse", userGroupResource.getIsBrowse());
                        request.getSession().setAttribute("isAdmin", userGroupResource.getIsAdmin());
                        request.getSession().setAttribute("isAssign", userGroupResource.getIsAssign());
                    }
                    //得到资源
                    com.zs.domain.basic.Resource resource =  findResourceService.get(userGroupResource.getResourceId());
                    if(null != resource) {
                        //得到菜单
                        Menu menu = findMenuService.get(resource.getMenuId());
                        List<com.zs.domain.basic.Resource> resourceList = menuResourceMap.get(menu.getName());
                        if (null == resourceList) {
                            resourceList = new ArrayList<com.zs.domain.basic.Resource>();
                        }
                        resourceList.add(resource);
                        menuResourceMap.put(menu.getName(), resourceList);
                    }
                }
            }
        }
        return menuResourceMap;
    }
}
