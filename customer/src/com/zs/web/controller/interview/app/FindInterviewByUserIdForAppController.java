package com.zs.web.controller.interview.app;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customer.FindCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.interview.FindInterviewByWhereService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import com.zs.web.controller.interview.FindInterviewByWhereController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016/12/27.
 */
@Controller
@RequestMapping(value = "/findInterviewByUserIdForApp")
public class FindInterviewByUserIdForAppController extends LoggerController {
    private static Logger log = Logger.getLogger(FindInterviewByWhereController.class);

    @Resource
    private FindInterviewByWhereService findInterviewByWhereService;
    @Resource
    private FindCustomerService findCustomerService;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;


    @RequestMapping(value = "find")
    public String find(@RequestParam("customerId") long customerId,
                       @RequestParam("linkmanId") long linkmanId,
                       HttpServletRequest request){
        try{

            int loginLevel = UserTools.getLoginUserForLevel(request);
            String loginZzCode = UserTools.getLoginUserForZzCode(request);
            long loginId = UserTools.getLoginUserForId(request);

            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);

            //客户信息
            Customer customer = findCustomerService.get(customerId);
            CustomerLankman customerLankman = findLinkmanByCustomerIdService.get(linkmanId);


            Map<String, String> params = new HashMap<String, String>();
            params.put("linkmanId", linkmanId+"");
            params.put("loginLevel", loginLevel+"");
            params.put("isBrowse", isBrowse+"");
            params.put("loginZzCode", loginZzCode);
            params.put("loginId", loginId+"");
            PageInfo pageInfo = getPageInfo(request);
            pageInfo.setCountOfCurrentPage(999999);
            pageInfo = findInterviewByWhereService.findPageByWhere(pageInfo, params);

            request.setAttribute("customer", customer);
            request.setAttribute("customerLankman", customerLankman);
            request.setAttribute("interviewList", pageInfo.getPageResults());
            request.setAttribute("loginZzCode", loginZzCode);

            return "app/findInterviewByUserId";
        }
        catch(Exception e){
            super.outputException(request, e, log, "查询访谈信息");
            return "error";
        }
    }
}
