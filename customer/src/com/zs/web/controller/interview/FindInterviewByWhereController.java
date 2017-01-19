package com.zs.web.controller.interview;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
import com.zs.service.basic.user.FindUserByParenSignService;
import com.zs.service.basic.user.ValidateLoginService;
import com.zs.service.customer.FindCustomerService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
import com.zs.service.interview.FindInterviewByWhereService;
import com.zs.tools.DateTools;
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
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/findInterviewByWhere")
public class FindInterviewByWhereController extends LoggerController {
    private static Logger log = Logger.getLogger(FindInterviewByWhereController.class);

    @Resource
    private FindInterviewByWhereService findInterviewByWhereService;
    @Resource
    private FindCustomerTypeService findCustomerTypeService;
    @Resource
    private FindCustomerStateService findCustomerStateService;
    @Resource
    private ValidateLoginService validateLoginService;
    @Resource
    private FindCustomerService findCustomerService;
    @Resource
    private FindUserByParenSignService findUserByParenSignService;


    @RequestMapping(value = "find")
    public String find(@RequestParam(value="s_customerId", required=false, defaultValue="") String customerId,
                       @RequestParam(value="s_userId", required=false, defaultValue="") String userId,
                       @RequestParam(value="s_typeId", required=false, defaultValue="") String typeId,
                       @RequestParam(value="s_stateId", required=false, defaultValue="") String stateId,
                       @RequestParam(value="s_beginDate", required=false, defaultValue="") String beginDate,
                       @RequestParam(value="s_endDate", required=false, defaultValue="") String endDate,
                       HttpServletRequest request){
        try{
            int loginLevel = UserTools.getLoginUserForLevel(request);
            String loginZzCode = UserTools.getLoginUserForZzCode(request);
            long loginId = UserTools.getLoginUserForId(request);

            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);

            //客户信息
            List<Customer> customerList = null;
            if(loginLevel == User.LEVEL_COMPANY) {
                customerList = findCustomerService.find();
            }else{
                if(UserGroupResource.ISBROWSE_ME == isBrowse){
                    customerList = findCustomerService.findForMe(loginZzCode, loginId);
                }else{
                    customerList = findCustomerService.findForChild(loginZzCode);
                }
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

            Map<String, String> params = new HashMap<String, String>();
            params.put("customerId", customerId);
            params.put("userId", userId);
            params.put("typeId", typeId);
            params.put("stateId", stateId);
            params.put("beginDate", beginDate);
            params.put("endDate", endDate);

            params.put("loginLevel", loginLevel+"");
            params.put("isBrowse", isBrowse+"");
            params.put("loginZzCode", loginZzCode);
            params.put("loginId", loginId+"");

            PageInfo pageInfo = getPageInfo(request);
            pageInfo = findInterviewByWhereService.findPageByWhere(pageInfo, params);

            request.setAttribute("pageInfo", pageInfo);
            request.setAttribute("userList", userList);
            request.setAttribute("typeList", customerTypeList);
            request.setAttribute("stateList", customerStateList);
            request.setAttribute("customerList", customerList);
            request.setAttribute("nowDate", DateTools.getThisYear()+"-"+DateTools.getThisMonth()+"-"+DateTools.getThisDay());
            request.setAttribute("loginZzCode", loginZzCode);
            return "interview/interviewList";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询访谈信息");
            return "error";
        }
    }
}
