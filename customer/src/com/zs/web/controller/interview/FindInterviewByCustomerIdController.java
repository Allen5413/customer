package com.zs.web.controller.interview;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.basic.User;
import com.zs.domain.customer.Customer;
import com.zs.service.basic.user.FindUserPageByWhereService;
import com.zs.service.customer.FindCustomerService;
import com.zs.service.interview.FindInterviewByWhereService;
import com.zs.tools.UserTools;
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
 * Created by Allen on 2016/6/6.
 */
@Controller
@RequestMapping(value = "/findInterviewByCustomerId")
public class FindInterviewByCustomerIdController extends LoggerController {
    private static Logger log = Logger.getLogger(FindInterviewByWhereController.class);

    @Resource
    private FindInterviewByWhereService findInterviewByWhereService;
    @Resource
    private FindCustomerService findCustomerService;
    @Resource
    private FindUserPageByWhereService findUserPageByWhereService;


    @RequestMapping(value = "find")
    public String find(@RequestParam("customerId") String customerId,
                       HttpServletRequest request){
        try{

            Customer customer = findCustomerService.get(Long.parseLong(customerId));
            User user = findUserPageByWhereService.get(customer.getUserId());

            Map<String, String> params = new HashMap<String, String>();
            params.put("customerId", customerId);
            params.put("loginLevel", UserTools.getLoginUserForLevel(request)+"");
            params.put("isBrowse", UserTools.getLoginUserForIsBrowse(request)+"");
            params.put("loginZzCode", UserTools.getLoginUserForZzCode(request));
            params.put("loginId", UserTools.getLoginUserForId(request)+"");
            PageInfo pageInfo = getPageInfo(request);
            pageInfo.setCountOfCurrentPage(999999);
            pageInfo = findInterviewByWhereService.findPageByWhere(pageInfo, params);

            request.setAttribute("pageInfo", pageInfo);
            request.setAttribute("customer", customer);
            request.setAttribute("user", user);

            return "interview/interviewByCustomerIdList";
        }
        catch(Exception e){
            super.outputException(request, e, log, "查询访谈信息");
            return "error";
        }
    }
}
