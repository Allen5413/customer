package com.zs.web.controller.customer;

import com.zs.domain.customer.Customer;
import com.zs.service.basic.area.FindAreaForProvinceService;
import com.zs.service.basic.school.FindSchoolForNotExistsService;
import com.zs.service.customer.AddCustomerService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeForStateYesService;
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
 * Created by Allen on 2016/3/7.
 */
@Controller
@RequestMapping(value = "/addCustomer")
public class AddCustomerController extends
        LoggerController<Customer, AddCustomerService> {
    private static Logger log = Logger.getLogger(AddCustomerController.class);

    @Resource
    private AddCustomerService addCustomerService;
    @Resource
    private FindSchoolForNotExistsService findSchoolForNotExistsService;
    @Resource
    private FindAreaForProvinceService findAreaForProvinceService;
    @Resource
    private FindCustomerStateService findCustomerStateService;
    @Resource
    private FindCustomerTypeForStateYesService findCustomerTypeForStateYesService;

    /**
     * 打开新增页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        try {
            request.setAttribute("schoolList", findSchoolForNotExistsService.find());
            request.setAttribute("provinceList", findAreaForProvinceService.find());
            request.setAttribute("stateList", findCustomerStateService.findAll());
            request.setAttribute("typeList", findCustomerTypeForStateYesService.find());
        } catch (Exception e) {
            return "error";
        }
        return "customer/customerAdd";
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Customer customer,
                          @RequestParam(value = "linkmanInfo", required = false, defaultValue = "")String linkmanInfo){
        JSONObject jsonObject = new JSONObject();
        try{
            addCustomerService.add(customer, linkmanInfo, request);
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "新增用户");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
