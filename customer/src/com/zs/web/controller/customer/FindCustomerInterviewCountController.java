package com.zs.web.controller.customer;

import com.zs.service.customer.FindCustomerInterviewCountService;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/6/6.
 */
@Controller
@RequestMapping(value = "/findCustomerInterviewCount")
public class FindCustomerInterviewCountController extends LoggerController {
    private static Logger log = Logger.getLogger(FindCustomerByWhereController.class);

    @Resource
    private FindCustomerInterviewCountService findCustomerInterviewCountService;


    @RequestMapping(value = "find")
    public String find(HttpServletRequest request){
        try{
            List<Map<String, String>> list = findCustomerInterviewCountService.find(request);
            request.setAttribute("list", list);
            return "customer/customerInterviewCount";
        }
        catch(Exception e){
            super.outputException(request, e, log, "查询统计客户访谈记录");
            return "error";
        }
    }
}
