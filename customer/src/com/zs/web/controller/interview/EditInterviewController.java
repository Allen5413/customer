package com.zs.web.controller.interview;

import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.Interview;
import com.zs.service.basic.area.FindAreaForProvinceService;
import com.zs.service.customer.AddCustomerService;
import com.zs.service.customer.EditCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
import com.zs.service.interview.EditInterviewService;
import com.zs.tools.HttpRequestTools;
import com.zs.tools.IpTools;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = "/editInterview")
public class EditInterviewController extends
        LoggerController<Customer, EditCustomerService> {
    private static Logger log = Logger.getLogger(EditInterviewController.class);

    @Resource
    private EditInterviewService editInterviewService;
    @Resource
    private AddCustomerService addCustomerService;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;

    /**
     * 打开页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("id")long id){
        try {
            //查询信息
            Interview interview = editInterviewService.get(id);
            //查询客户
            Customer customer = addCustomerService.get(interview.getCustomerId());
            //查询该客户的联系人
            List<CustomerLankman> linkmanList = findLinkmanByCustomerIdService.find(interview.getCustomerId());
            //获取当前ip地址
            String ip = IpTools.getIpAddress(request);
            String address = HttpRequestTools.getAddressByIp(ip);

            request.setAttribute("customer", customer);
            request.setAttribute("interview", interview);
            request.setAttribute("linkmanList", linkmanList);
            request.setAttribute("ip", ip);
            request.setAttribute("address", address);

        } catch (Exception e) {
            super.outputException(request, e, log, "打开页面");
            return "error";
        }
        return "interview/interviewEdit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request,
                          @RequestParam("id")long id,
                          @RequestParam("linkmanId")long linkmanId,
                          @RequestParam("content")String content,
                          @RequestParam(value = "ip", required = false, defaultValue = "")String ip,
                          @RequestParam(value = "address", required = false, defaultValue = "")String address){
        JSONObject jsonObject = new JSONObject();
        try{
            if(StringUtils.isEmpty(ip)){
                ip = IpTools.getIpAddress(request);
                address = HttpRequestTools.getAddressByIp(ip);
            }
            editInterviewService.edit(id, linkmanId, content, UserTools.getLoginUserForZzCode(request), ip, address);
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "编辑访谈记录");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
