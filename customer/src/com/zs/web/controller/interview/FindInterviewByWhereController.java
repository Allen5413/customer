package com.zs.web.controller.interview;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.User;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
import com.zs.service.basic.user.ValidateLoginService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
import com.zs.service.interview.FindInterviewByWhereService;
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


    @RequestMapping(value = "find")
    public String find(@RequestParam(value="userId", required=false, defaultValue="") String userId,
                       @RequestParam(value="typeId", required=false, defaultValue="") String typeId,
                       @RequestParam(value="stateId", required=false, defaultValue="") String stateId,
                       @RequestParam(value="beginDate", required=false, defaultValue="") String beginDate,
                       @RequestParam(value="endDate", required=false, defaultValue="") String endDate,
                       HttpServletRequest request){
        try{
            //获取客户经理
            List<User> userList = validateLoginService.getAll();
            //获取客户类型
            List<CustomerType> customerTypeList = findCustomerTypeService.findAll();
            //获取客户状态
            List<CustomerState> customerStateList = findCustomerStateService.findAll();

            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("typeId", typeId);
            params.put("stateId", stateId);
            params.put("beginDate", beginDate);
            params.put("endDate", endDate);
            PageInfo pageInfo = getPageInfo(request);
            pageInfo = findInterviewByWhereService.findPageByWhere(pageInfo, params);

            request.setAttribute("pageInfo", pageInfo);
            request.setAttribute("userList", userList);
            request.setAttribute("typeList", customerTypeList);
            request.setAttribute("stateList", customerStateList);
            return "interview/interviewList";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询访谈信息");
            return "error";
        }
    }
}
