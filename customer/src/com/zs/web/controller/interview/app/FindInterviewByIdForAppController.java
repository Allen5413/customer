package com.zs.web.controller.interview.app;

import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.domain.basic.User;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.Interview;
import com.zs.domain.customer.InterviewFile;
import com.zs.service.customer.FindCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.interview.FindInterviewByWhereService;
import com.zs.service.interviewfile.FindInterviewFileByInterviewIdService;
import com.zs.web.controller.LoggerController;
import com.zs.web.controller.interview.FindInterviewByWhereController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Allen on 2016/12/27.
 */
@Controller
@RequestMapping(value = "/findInterviewByIdForApp")
public class FindInterviewByIdForAppController extends LoggerController {
    private static Logger log = Logger.getLogger(FindInterviewByWhereController.class);

    @Resource
    private FindInterviewByWhereService findInterviewByWhereService;
    @Resource
    private FindCustomerService findCustomerService;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;
    @Resource
    private FindInterviewFileByInterviewIdService findInterviewFileByInterviewIdService;
    @Resource
    private FindUserByZZDAO findUserByZZDAO;


    @RequestMapping(value = "find")
    public String find(@RequestParam("id") long id,
                       HttpServletRequest request){
        try{
            //查询访谈记录
            Interview interview = findInterviewByWhereService.get(id);
            //客户信息
            Customer customer = findCustomerService.get(interview.getCustomerId());
            //联系人
            CustomerLankman customerLankman = findLinkmanByCustomerIdService.get(interview.getCustomerLankmanId());
            //访谈记录附件
            List<InterviewFile> interviewFileList = findInterviewFileByInterviewIdService.find(interview.getId());
            User user = findUserByZZDAO.find(interview.getOperator());

            request.setAttribute("customer", customer);
            request.setAttribute("customerLankman", customerLankman);
            request.setAttribute("interview", interview);
            request.setAttribute("interviewFileList", interviewFileList);
            request.setAttribute("user", user);
            return "app/interviewInfo";
        }
        catch(Exception e){
            super.outputException(request, e, log, "查询访谈信息");
            return "error";
        }
    }
}
