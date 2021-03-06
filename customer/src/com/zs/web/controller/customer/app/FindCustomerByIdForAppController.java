package com.zs.web.controller.customer.app;

import com.feinno.framework.common.exception.BusinessException;
import com.zs.domain.basic.Area;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
import com.zs.service.basic.area.FindAreaByCodeService;
import com.zs.service.customer.EditCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
import com.zs.web.controller.LoggerController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
@Controller
@RequestMapping(value = "/findCustomerByIdForApp")
public class FindCustomerByIdForAppController extends LoggerController {
    private static Logger log = Logger.getLogger(FindCustomerByIdForAppController.class);

    @Resource
    private EditCustomerService editCustomerService;
    @Resource
    private FindAreaByCodeService findAreaByCodeService;
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
                       @RequestParam("id")long id,
                       @RequestParam(value = "num", required = false, defaultValue = "5")int num){
        try {
            //查询客户信息
            Customer customer = editCustomerService.get(id);
            if(null == customer){
                throw new BusinessException("没有找到客户信息");
            }
            //查询客户联系人信息
            List<CustomerLankman> linkmanList = findLinkmanByCustomerIdService.find(id);
            //查询省份
            Area area = findAreaByCodeService.find(customer.getProvinceCode());
            //查询客户类型
            CustomerState customerState = findCustomerStateService.get(customer.getCustomerStateId());
            //查询客户状态
            CustomerType customerType = findCustomerTypeService.get(customer.getCustomerTypeId());

            request.setAttribute("customer", customer);
            request.setAttribute("linkmanList", linkmanList);
            request.setAttribute("area", area);
            request.setAttribute("customerState", customerState);
            request.setAttribute("customerType", customerType);
        } catch (Exception e) {
            super.outputException(request, e, log, "查看客户详情");
            return "error";
        }
        return "app/customerInfo";
    }
}
