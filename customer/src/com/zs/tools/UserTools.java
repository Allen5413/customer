package com.zs.tools;

import com.zs.domain.basic.Resource;
import com.zs.domain.basic.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 获取登录用户相关信息
 * Created by Allen on 2015/4/27.
 */
public class UserTools {

    /**
     * 获取登录用户的登录ID
     * @param request
     * @return
     */
    public static Long getLoginUserForId(HttpServletRequest request){
        if(null != request.getSession().getAttribute("userId")){
            return Long.parseLong(request.getSession().getAttribute("userId").toString());
        }else{
            return null;
        }
    }

    /**
     * 获取登录用户的登录ZZ
     * @param request
     * @return
     */
    public static String getLoginUserForZzCode(HttpServletRequest request){
        if(null != request.getSession().getAttribute("zzCode")){
            return request.getSession().getAttribute("zzCode").toString();
        }else{
            return "";
        }
    }

    /**
     * 获取登录用户的用户姓名
     * @param request
     * @return
     */
    public static String getLoginUserForName(HttpServletRequest request){
        if(null != request.getSession().getAttribute("name")){
            return request.getSession().getAttribute("name").toString();
        }else{
            return "";
        }
    }

    /**
     * 获取登录用户是否能浏览的权限
     * @param request
     * @return
     */
    public static Integer getLoginUserForIsBrowse(HttpServletRequest request){
        if(null != request.getSession().getAttribute("isBrowse")){
            return Integer.parseInt(request.getSession().getAttribute("isBrowse").toString());
        }else{
            return null;
        }
    }

    /**
     * 获取登录用户的用户是否能管理的权限
     * @param request
     * @return
     */
    public static Integer getLoginUserForIsAdmin(HttpServletRequest request){
        if(null != request.getSession().getAttribute("isAdmin")){
            return Integer.parseInt(request.getSession().getAttribute("isAdmin").toString());
        }else{
            return null;
        }
    }

    /**
     * 获取登录用户是否能指派的权限
     * @param request
     * @return
     */
    public static Integer getLoginUserForIsAssign(HttpServletRequest request){
        if(null != request.getSession().getAttribute("isAssign")){
            return Integer.parseInt(request.getSession().getAttribute("isAssign").toString());
        }else{
            return null;
        }
    }

    /**
     * 得到登录用户的所属菜单资源信息
     * @param request
     * @return
     */
    public static Map<String, List<Resource>> getLoginUserForMenu(HttpServletRequest request){
        return (Map<String, List<Resource>>)request.getSession().getAttribute("menuMap");
    }
}
