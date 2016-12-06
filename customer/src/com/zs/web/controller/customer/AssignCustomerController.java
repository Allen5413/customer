package com.zs.web.controller.customer;

import com.zs.domain.basic.Area;
import com.zs.domain.basic.User;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.CustomerType;
import com.zs.service.basic.area.FindAreaByCodeService;
import com.zs.service.basic.user.FindUserByParenSignService;
import com.zs.service.basic.user.FindUserForStateEnableService;
import com.zs.service.customer.AssignCustomerService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeService;
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
 * 给客户指派经理
 * Created by Allen on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/assignCustomer")
public class AssignCustomerController extends
        LoggerController<Customer, AssignCustomerService> {
    private static Logger log = Logger.getLogger(AssignCustomerController.class);

    @Resource
    private AssignCustomerService assignCustomerService;
    @Resource
    private FindAreaByCodeService findAreaByCodeService;
    @Resource
    private FindCustomerStateService findCustomerStateService;
    @Resource
    private FindCustomerTypeService findCustomerTypeService;
    @Resource
    private FindUserForStateEnableService findUserForStateEnableService;
    @Resource
    private FindUserByParenSignService findUserByParenSignService;

    /**
     * 打开页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("id")long id){
        try {
            String loginZzCode = UserTools.getLoginUserForZzCode(request);
            int loginLevel = UserTools.getLoginUserForLevel(request);

            //查询客户信息
            Customer customer = assignCustomerService.get(id);
            //查询所在省份
            Area area = findAreaByCodeService.find(customer.getProvinceCode());
            //查询客户类型
            CustomerType customerType = findCustomerTypeService.get(customer.getCustomerTypeId());
            //查询客户状态
            CustomerState customerState = findCustomerStateService.get(customer.getCustomerStateId());
            //获取客户经理, 如果是公司级别的斗查询所有的，不是就查询自己以及自己下属的
            List<User> userList = null;
            if(loginLevel == User.LEVEL_COMPANY) {
                userList = findUserForStateEnableService.getAll();
            }else{
                userList = findUserByParenSignService.find(loginZzCode);
            }

            request.setAttribute("customer", customer);
            request.setAttribute("area", area);
            request.setAttribute("customerType", customerType);
            request.setAttribute("customerState", customerState);
            request.setAttribute("userList", userList);
        } catch (Exception e) {
            super.outputException(request, e, log, "打开指派功能");
            return "error";
        }
        return "customer/customerAssign";
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "assign")
    @ResponseBody
    public JSONObject assign(HttpServletRequest request,
                             @RequestParam("id")long id,
                             @RequestParam("userId")long userId){
        JSONObject jsonObject = new JSONObject();
        try{
            //查询客户信息
            assignCustomerService.assign(id, userId, UserTools.getLoginUserForZzCode(request));
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "指派");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
