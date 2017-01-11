package com.zs.web.controller.customer;

import com.zs.domain.customer.Customer;
import com.zs.service.basic.area.FindAreaForProvinceService;
import com.zs.service.customer.EditCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
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
 * Created by Allen on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/editCustomer")
public class EditCustomerController extends
        LoggerController<Customer, EditCustomerService> {
    private static Logger log = Logger.getLogger(EditCustomerController.class);

    @Resource
    private EditCustomerService editCustomerService;
    @Resource
    private FindAreaForProvinceService findAreaForProvinceService;
    @Resource
    private FindCustomerStateService findCustomerStateService;
    @Resource
    private FindCustomerTypeService findCustomerTypeService;
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
            Customer customer = editCustomerService.get(id);
            //查询客户联系人信息
            List<com.alibaba.fastjson.JSONObject> linkmanList = findLinkmanByCustomerIdService.findForInterviewCount(id);

            request.setAttribute("customer", customer);
            request.setAttribute("linkmanList", linkmanList);
            request.setAttribute("provinceList", findAreaForProvinceService.find());
            request.setAttribute("stateList", findCustomerStateService.findAll());
            request.setAttribute("typeList", findCustomerTypeService.findAll());
        } catch (Exception e) {
            return "error";
        }
        return "customer/customerEdit";
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, Customer customer,
                          @RequestParam(value = "linkmanInfo", required = false, defaultValue = "")String linkmanInfo,
                          @RequestParam(value = "delLinkman", required = false, defaultValue = "")String delLinkman){
        JSONObject jsonObject = new JSONObject();
        try{
            //获取当前ip地址
            String ip = IpTools.getIpAddress(request);
            String address = HttpRequestTools.getAddressByIp(ip);
            editCustomerService.edit(customer, linkmanInfo, delLinkman, UserTools.getLoginUserForZzCode(request), ip, address, UserTools.getLoginUserForName(request));
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "编辑用户");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
