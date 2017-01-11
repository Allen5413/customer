package com.zs.web.controller.customertype;

import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.FindCustomerTypeTotalCountService;
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
@RequestMapping(value = "/findCutomerTypeTotalCount")
public class FindCustomerTypeTotalCountController extends
        LoggerController<CustomerType, FindCustomerTypeTotalCountService> {

    private static Logger log = Logger.getLogger(FindCustomerTypeTotalCountController.class);

    @Resource
    private FindCustomerTypeTotalCountService findCustomerTypeTotalCountService;

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "find")
    @ResponseBody
    public JSONObject find(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("list", findCustomerTypeTotalCountService.find());
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "统计客户类型数量");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
