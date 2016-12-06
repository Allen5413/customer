package com.zs.web.controller.customerlinkman;

import com.alibaba.fastjson.JSONObject;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customerlinkman.DelLinkmanByIdService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/delLinkmanById")
public class DelLinkmanByIdController extends
        LoggerController<CustomerLankman, DelLinkmanByIdService> {
    private static Logger log = Logger.getLogger(DelLinkmanByIdController.class);

    @Resource
    private DelLinkmanByIdService delLinkmanByIdService;


    /**
     *
     * @return
     */
    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(HttpServletRequest request,
                       @RequestParam("id")long id){
        JSONObject jsonObject = new JSONObject();
        try {
            delLinkmanByIdService.del(id, UserTools.getLoginUserForZzCode(request));
            jsonObject.put("state", 0);
        } catch (Exception e) {
            String msg = super.outputException(request, e, log, "删除客户联系人");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
