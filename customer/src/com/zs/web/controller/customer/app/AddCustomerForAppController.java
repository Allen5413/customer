package com.zs.web.controller.customer.app;

import com.zs.domain.basic.User;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerState;
import com.zs.domain.customer.Interview;
import com.zs.service.basic.area.FindAreaForProvinceService;
import com.zs.service.basic.user.FindUserByParenSignService;
import com.zs.service.customer.AddCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.customerstate.FindCustomerStateForStateYesService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.customertype.FindCustomerTypeForStateYesService;
import com.zs.service.interview.AddInterviewService;
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
 * Created by Allen on 2016/3/7.
 */
@Controller
@RequestMapping(value = "/addCustomerForApp")
public class AddCustomerForAppController extends
        LoggerController<Customer, AddCustomerService> {
    private static Logger log = Logger.getLogger(AddCustomerForAppController.class);

    @Resource
    private AddCustomerService addCustomerService;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;
    @Resource
    private FindAreaForProvinceService findAreaForProvinceService;
    @Resource
    private FindCustomerStateForStateYesService findCustomerStateForStateYesService;
    @Resource
    private FindCustomerTypeForStateYesService findCustomerTypeForStateYesService;
    @Resource
    private FindUserByParenSignService findUserByParenSignService;
    @Resource
    private AddInterviewService addInterviewService;
    @Resource
    private FindCustomerStateService findCustomerStateService;

    /**
     * 打开新增页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        try {
            //获取客户经理, 如果是公司级别的斗查询所有的，不是就查询自己以及自己下属的
            List<User> userList = null;
            if(UserTools.getLoginUserForLevel(request) == User.LEVEL_COMPANY) {
                userList = findUserByParenSignService.getAll();
            }else{
                userList = findUserByParenSignService.find(UserTools.getLoginUserForZzCode(request));
            }
            request.setAttribute("userList", userList);
            request.setAttribute("provinceList", findAreaForProvinceService.find());
            request.setAttribute("stateList", findCustomerStateForStateYesService.find());
            request.setAttribute("typeList", findCustomerTypeForStateYesService.find());
            request.setAttribute("isBrowse", UserTools.getLoginUserForIsBrowse(request));
            request.setAttribute("loginId", UserTools.getLoginUserForId(request));
            request.setAttribute("loginName", UserTools.getLoginUserForName(request));
        } catch (Exception e) {
            return "error";
        }
        return "app/addCustomer";
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Customer customer,
                          @RequestParam(value = "linkmanInfo", required = false, defaultValue = "")String linkmanInfo,
                          @RequestParam(value = "ip_address", required = false, defaultValue = "")String ip_address){
        JSONObject jsonObject = new JSONObject();
        try{
            addCustomerService.add(customer, linkmanInfo, request);
            //查询客户联系人信息
            List<com.alibaba.fastjson.JSONObject> linkmanList = findLinkmanByCustomerIdService.findForInterviewCount(customer.getId());
            if(null != linkmanList && 0 < linkmanList.size()){
                //获取当前ip地址
                String ip = IpTools.getIpAddress(request);
                //获取客户状态
                CustomerState customerState = findCustomerStateService.get(customer.getCustomerStateId());
                com.alibaba.fastjson.JSONObject json = linkmanList.get(0);
                long linkmanId = Long.parseLong(json.get("id").toString());
                Interview interview = new Interview();
                interview.setCustomerId(customer.getId());
                interview.setCustomerLankmanId(linkmanId);
                interview.setIp(ip);
                interview.setAddress(ip_address);
                interview.setContent(UserTools.getLoginUserForName(request) + "创建状态为\"" + customerState.getName() + "\"");
                interview.setCreator(UserTools.getLoginUserForZzCode(request));
                interview.setOperator(UserTools.getLoginUserForZzCode(request));
                addInterviewService.add(interview, null);
            }
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "新增用户");
            if(msg.indexOf("for key 'creator'") > 0 || msg.indexOf("已经存在") > 0){
                jsonObject.put("state", 0);
            }else {
                jsonObject.put("state", 1);
                jsonObject.put("msg", msg);
            }
        }
        return jsonObject;
    }
}
