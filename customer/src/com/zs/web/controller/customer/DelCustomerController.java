package com.zs.web.controller.customer;

import com.zs.domain.customer.Customer;
import com.zs.service.customer.DelCustomerByIdService;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/delCustomer")
public class DelCustomerController extends
        LoggerController<Customer, DelCustomerByIdService> {
    private static Logger log = Logger.getLogger(DelCustomerController.class);

    @Resource
    private DelCustomerByIdService delCustomerByIdService;

    /**
     * 删除客户记录
     * @param request
     * @return
     */
    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(HttpServletRequest request,
                          @RequestParam("ids")String ids){
        JSONObject jsonObject = new JSONObject();
        try{
            delCustomerByIdService.del(ids.split(","));
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "删除客户记录");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
