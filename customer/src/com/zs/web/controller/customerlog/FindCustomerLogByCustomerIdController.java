package com.zs.web.controller.customerlog;

import com.zs.domain.customer.CustomerLog;
import com.zs.service.customerlog.FindCustomerLogByCustomerIdService;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/findCustomerLogByCustomerId")
public class FindCustomerLogByCustomerIdController extends
        LoggerController<CustomerLog, FindCustomerLogByCustomerIdService> {
    private static Logger log = Logger.getLogger(FindCustomerLogByCustomerIdController.class);

    @Resource
    private FindCustomerLogByCustomerIdService findCustomerLogByCustomerIdService;

    /**
     * 打开页面
     * @return
     */
    @RequestMapping(value = "find")
    public String find(HttpServletRequest request,
                       @RequestParam("customerId")long customerId){
        try {
            List<JSONObject> customerLogList = findCustomerLogByCustomerIdService.find(customerId);
            request.setAttribute("customerLogList", customerLogList);
        } catch (Exception e) {
            super.outputException(request, e, log, "查询客户信息变更日志");
            return "error";
        }
        return "customer/customerLog";
    }
}
