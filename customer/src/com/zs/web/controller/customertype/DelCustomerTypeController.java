package com.zs.web.controller.customertype;

import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.DelCustomerTypeByIdService;
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
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/delCustomerType")
public class DelCustomerTypeController extends
        LoggerController<CustomerType, DelCustomerTypeByIdService> {
    private static Logger log = Logger.getLogger(DelCustomerTypeController.class);

    @Resource
    private DelCustomerTypeByIdService delCustomerTypeByIdService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("ids") String ids, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try{
            delCustomerTypeByIdService.del(ids.split(","));
            jsonObject.put("state", 0);
        } catch (Exception e){
            String msg = super.outputException(request, e, log, "删除客户类型");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
