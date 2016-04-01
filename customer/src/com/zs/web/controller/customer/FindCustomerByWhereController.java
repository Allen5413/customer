package com.zs.web.controller.customer;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.Area;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
import com.zs.service.basic.area.FindAreaForProvinceService;
import com.zs.service.basic.user.ValidateLoginService;
import com.zs.service.customer.FindCustomerByWhereService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
import com.zs.tools.HttpRequestTools;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/7.
 */
@Controller
@RequestMapping(value = "/findCustomerByWhere")
public class FindCustomerByWhereController extends LoggerController {
    private static Logger log = Logger.getLogger(FindCustomerByWhereController.class);

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


    @RequestMapping(value = "find")
    public String find(@RequestParam(value="s_userId", required=false, defaultValue="") String userId,
                       @RequestParam(value="s_typeId", required=false, defaultValue="") String typeId,
                       @RequestParam(value="s_stateId", required=false, defaultValue="") String stateId,
                       @RequestParam(value="s_name", required=false, defaultValue="") String name,
                       @RequestParam(value="s_provinceCode", required=false, defaultValue="") String provinceCode,
                       HttpServletRequest request){
        try{
            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);
            Integer isAdmin = UserTools.getLoginUserForIsAdmin(request);
            Integer isAssign = UserTools.getLoginUserForIsAssign(request);

            if(null == isBrowse){
                return "customer/customerList";
            }
            if(UserGroupResource.ISBROWSE_ME == isBrowse){
                userId = UserTools.getLoginUserForId(request)+"";
                request.setAttribute("userName", UserTools.getLoginUserForName(request));
            }

            //获取客户经理
            List<User> userList = validateLoginService.getAll();
            //获取客户类型
            List<CustomerType> customerTypeList = findCustomerTypeService.findAll();
            //获取客户状态
            List<CustomerState> customerStateList = findCustomerStateService.findAll();
            //获取省份
            List<Area> areaList = findAreaForProvinceService.find();

            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("typeId", typeId);
            params.put("stateId", stateId);
            params.put("provinceCode", provinceCode);
            params.put("name", name);
            PageInfo pageInfo = getPageInfo(request);
            pageInfo = findCustomerByWhereService.findPageByWhere(pageInfo, params);

            request.setAttribute("userId", UserTools.getLoginUserForId(request));
            request.setAttribute("isBrowse", isBrowse);
            request.setAttribute("isAdmin", isAdmin);
            request.setAttribute("isAssign", isAssign);
            request.setAttribute("pageInfo", pageInfo);
            request.setAttribute("userList", userList);
            request.setAttribute("typeList", customerTypeList);
            request.setAttribute("stateList", customerStateList);
            request.setAttribute("areaList", areaList);
            return "customer/customerList2";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询客户信息");
            return "error";
        }
    }


    @RequestMapping(value = "findNoCount")
    @ResponseBody
    public int findNoCount(@RequestParam("no")String no){
        try {
            return HttpRequestTools.getNoCount(no);
        } catch (Exception e) {
            return 0;
        }
    }
}
