package com.zs.web.controller.customerlinkman;

import com.alibaba.fastjson.JSONObject;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/findLinkmanByCustomerId")
public class FindLinkmanByCustomerIdController extends
        LoggerController<CustomerLankman, FindLinkmanByCustomerIdService> {
    private static Logger log = Logger.getLogger(FindLinkmanByCustomerIdController.class);

    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;

    /**
     * 打开页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("id")long id){
        request.setAttribute("customerId", id);
        return "customer/customerLinkMan";
    }


    /**
     *
     * @return
     */
    @RequestMapping(value = "find")
    @ResponseBody
    public JSONObject find(HttpServletRequest request,
                       @RequestParam("customerId")long customerId){
        JSONObject jsonObject = new JSONObject();
        try {
            //查询客户联系人信息
            List<JSONObject> linkmanList = findLinkmanByCustomerIdService.findForInterviewCount(customerId);
            jsonObject.put("linkmanList", linkmanList);
            jsonObject.put("state", 0);
        } catch (Exception e) {
            String msg = super.outputException(request, e, log, "打开客户联系人");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
