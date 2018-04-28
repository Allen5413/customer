package com.zs.web.controller.customer.app;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.Area;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
import com.zs.service.basic.area.FindAreaForProvinceService;
import com.zs.service.basic.user.FindUserByParenSignService;
import com.zs.service.basic.user.ValidateLoginService;
import com.zs.service.customer.FindCustomerByWhereService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/7.
 */
@Controller
@RequestMapping(value = "/findCustomerByWhereForApp")
public class FindCustomerByWhereForAppController extends LoggerController {
    private static Logger log = Logger.getLogger(FindCustomerByWhereForAppController.class);

    @Resource
    private FindCustomerByWhereService findCustomerByWhereService;
    @Resource
    private FindCustomerTypeService findCustomerTypeService;
    @Resource
    private FindCustomerStateService findCustomerStateService;
    @Resource
    private ValidateLoginService validateLoginService;
    @Resource
    private FindAreaForProvinceService findAreaForProvinceService;
    @Resource
    private FindUserByParenSignService findUserByParenSignService;

    @RequestMapping(value = "findByName")
    public String findByName(HttpServletRequest request,
                                   @RequestParam(value="name", required=false, defaultValue="") String name,
                                   @RequestParam(value="search", required=false, defaultValue="") String search){
        try{
            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);
            if(null == isBrowse){
                return "app/findCustomerByName";
            }
            name = URLDecoder.decode(name, "utf-8");
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name);
            PageInfo pageInfo = getPageInfo(request);
            pageInfo.setCountOfCurrentPage(999999);
            if("do".equals(search)) {
                this.findPageInfo(pageInfo, params, request);
            }
            request.setAttribute("name", name);
            return "app/findCustomerByName";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询客户信息");
            return "error";
        }
    }


    @RequestMapping(value = "find")
    public String find(HttpServletRequest request){
        try{
            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);
            if(null == isBrowse){
                return "app/customerList";
            }
            Map<String, String> params = new HashMap<String, String>();
            PageInfo pageInfo = getPageInfo(request);
            pageInfo.setCountOfCurrentPage(999999);
            this.findPageInfo(pageInfo, params, request);
            return "app/customerList";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询客户信息");
            return "error";
        }
    }

    @RequestMapping(value = "openFindByOther")
    public String openFindByOther(HttpServletRequest request,
                                  @RequestParam(value="typeId", required=false, defaultValue="") String typeId,
                                  @RequestParam(value="stateId", required=false, defaultValue="") String stateId,
                                  @RequestParam(value="provinceCode", required=false, defaultValue="") String provinceCode){
        try{
            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);
            if(null == isBrowse){
                return "app/findCustomerByOther";
            }
            Map<String, String> params = new HashMap<String, String>();
            params.put("typeId", typeId);
            params.put("stateId", stateId);
            params.put("provinceCode", provinceCode);
            PageInfo pageInfo = getPageInfo(request);
            pageInfo.setCountOfCurrentPage(999999);
            this.findPageInfo(pageInfo, params, request);
            return "app/findCustomerByOther";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询客户信息");
            return "error";
        }
    }

    protected void findPageInfo(PageInfo pageInfo, Map<String, String> params, HttpServletRequest request)throws Exception{
        String loginZzCode = UserTools.getLoginUserForZzCode(request);
        int loginLevel = UserTools.getLoginUserForLevel(request);
        long loginId = UserTools.getLoginUserForId(request);

        //得到当前登录用户的客户资料管理权限
        Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);
        Integer isAdmin = UserTools.getLoginUserForIsAdmin(request);
        Integer isAssign = UserTools.getLoginUserForIsAssign(request);

        if(UserGroupResource.ISBROWSE_ME == isBrowse){
            request.setAttribute("userName", UserTools.getLoginUserForName(request));
        }

        //获取客户经理, 如果是公司级别的斗查询所有的，不是就查询自己以及自己下属的
        List<User> userList = null;
        if(loginLevel == User.LEVEL_COMPANY) {
            userList = validateLoginService.getAll();
        }else{
            userList = findUserByParenSignService.find(loginZzCode);
        }
        //获取客户类型
        List<CustomerType> customerTypeList = findCustomerTypeService.findAll();
        //获取客户状态
        List<CustomerState> customerStateList = findCustomerStateService.findAll();
        //获取省份
        List<Area> areaList = findAreaForProvinceService.find();

        params.put("loginLevel", loginLevel+"");
        params.put("isBrowse", isBrowse+"");
        params.put("loginZzCode", loginZzCode);
        params.put("loginId", loginId+"");
        pageInfo = findCustomerByWhereService.findPageByWhere(pageInfo, params);

        request.setAttribute("userId", UserTools.getLoginUserForId(request));
        request.setAttribute("isBrowse", isBrowse);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("isAssign", isAssign);
        request.setAttribute("customerList", pageInfo.getPageResults());
        request.setAttribute("userList", userList);
        request.setAttribute("typeList", customerTypeList);
        request.setAttribute("stateList", customerStateList);
        request.setAttribute("areaList", areaList);
    }
}
