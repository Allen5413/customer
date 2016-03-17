package com.zs.web.controller.customerstate;

import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.DelCustomerStateByIdService;
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
@RequestMapping(value = "/delCustomerState")
public class DelCustomerStateController extends
        LoggerController<CustomerState, DelCustomerStateByIdService> {
    private static Logger log = Logger.getLogger(DelCustomerStateController.class);

    @Resource
    private DelCustomerStateByIdService delCustomerStateByIdService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("ids") String ids, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try{
            delCustomerStateByIdService.del(ids.split(","));
            jsonObject.put("state", 0);
        } catch (Exception e){
            String msg = super.outputException(request, e, log, "删除客户状态");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
