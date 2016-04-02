package com.zs.web.controller.basic.customset;

import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
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
 * 自定义类别设置
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/findCustomset")
public class FindCustomsetController extends LoggerController {
    private static Logger log = Logger.getLogger(FindCustomsetController.class);

    @Resource
    private FindCustomerTypeService findCustomerTypeService;
    @Resource
    private FindCustomerStateService findCustomerStateService;

    /**
     * 查询类别还是状态，默认为类型
     * @param type  1：类型；2：状态
     * @param request
     * @return
     */
    @RequestMapping(value = "find")
    public String find(@RequestParam(value="s_type", required=false, defaultValue="1") String type,
                       HttpServletRequest request){
        try{
            //获取客户类型
            if("1".equals(type)) {
                List<CustomerType> customerTypeList = findCustomerTypeService.findAll();
                request.setAttribute("typeList", customerTypeList);
                return "customset/typeList";
            }
            //获取客户状态
            if("2".equals(type)) {
                List<CustomerState> customerStateList = findCustomerStateService.findAll();
                request.setAttribute("stateList", customerStateList);
                return "customset/stateList";
            }
            return "error";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询客户信息");
            return "error";
        }
    }
}
