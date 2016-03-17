package com.zs.web.controller.customerstate;

import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.AddCustomerStateService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/addCustomerState")
public class AddCutomerStateController extends
        LoggerController<CustomerState, AddCustomerStateService> {

    private static Logger log = Logger.getLogger(AddCutomerStateController.class);

    @Resource
    private AddCustomerStateService addCustomerStateService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "customset/customerStateAdd";
    }

    /**
     * 新增
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, CustomerState customerState){
        JSONObject jsonObject = new JSONObject();
        try{
            if(null != customerState) {
                addCustomerStateService.add(customerState, UserTools.getLoginUserForZzCode(request));
            }
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "新增客户状态");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
