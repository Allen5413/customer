package com.zs.web.controller.customertype;

import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.AddCustomerTypeService;
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
@RequestMapping(value = "/addCustomerType")
public class AddCutomerTypeController extends
        LoggerController<CustomerType, AddCustomerTypeService> {

    private static Logger log = Logger.getLogger(AddCutomerTypeController.class);

    @Resource
    private AddCustomerTypeService addCustomerTypeService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "customset/customerTypeAdd";
    }

    /**
     * 新增
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, CustomerType customerType){
        JSONObject jsonObject = new JSONObject();
        try{
            if(null != customerType) {
                addCustomerTypeService.add(customerType, UserTools.getLoginUserForZzCode(request));
            }
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "新增客户类型");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
