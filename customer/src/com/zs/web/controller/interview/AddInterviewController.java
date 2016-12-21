package com.zs.web.controller.interview;

import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.Interview;
import com.zs.service.customer.AddCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.interview.AddInterviewService;
import com.zs.tools.HttpRequestTools;
import com.zs.tools.IpTools;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/addInterview")
public class AddInterviewController extends
        LoggerController<Customer, AddCustomerService> {
    private static Logger log = Logger.getLogger(AddInterviewController.class);

    @Resource
    private AddCustomerService addCustomerService;
    @Resource
    private AddInterviewService addInterviewService;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;


    /**
     * 打开新增页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("id")long id){
        try {
            //查询客户信息
            Customer customer = addCustomerService.get(id);
            //查询该客户的联系人
            List<CustomerLankman> linkmanList = findLinkmanByCustomerIdService.find(id);
            //获取当前ip地址
            String ip = IpTools.getIpAddress(request);
            String address = HttpRequestTools.getAddressByIp(ip);
            request.setAttribute("customer", customer);
            request.setAttribute("linkmanList", linkmanList);
            request.setAttribute("ip", ip);
            request.setAttribute("address", address);
        } catch (Exception e) {
            super.outputException(request, e, log, "打开新增页面");
            return "error";
        }
        return "interview/interviewAdd";
    }

    /**
     * 新增访谈记录
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Interview interview){
        JSONObject jsonObject = new JSONObject();
        try{
            addInterviewService.add(interview, request);
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "新增访谈记录");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
