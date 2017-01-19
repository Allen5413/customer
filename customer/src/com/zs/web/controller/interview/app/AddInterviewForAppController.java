package com.zs.web.controller.interview.app;

import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.Interview;
import com.zs.service.customer.AddCustomerService;
import com.zs.service.customer.FindCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.interview.AddInterviewService;
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
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/addInterviewForApp")
public class AddInterviewForAppController extends
        LoggerController<Customer, AddCustomerService> {
    private static Logger log = Logger.getLogger(AddInterviewForAppController.class);

    @Resource
    private AddCustomerService addCustomerService;
    @Resource
    private AddInterviewService addInterviewService;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;
    @Resource
    private FindCustomerService findCustomerService;

    /**
     * 打开新增页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam(value = "customerId", required = false, defaultValue = "")String customerId,
                       @RequestParam(value = "linkmanId", required = false, defaultValue = "")String linkmanId){
        try {
            if(!StringUtils.isEmpty(customerId)){
                //查询客户信息
                Customer customer = addCustomerService.get(Long.parseLong(customerId));
                request.setAttribute("customer", customer);
            }
            if(!StringUtils.isEmpty(linkmanId)){
                //查询联系人
                CustomerLankman customerLankman = findLinkmanByCustomerIdService.get(Long.parseLong(linkmanId));
                request.setAttribute("customerLankman", customerLankman);
            }


            if(StringUtils.isEmpty(customerId)){
                int loginLevel = UserTools.getLoginUserForLevel(request);
                String loginZzCode = UserTools.getLoginUserForZzCode(request);
                long loginId = UserTools.getLoginUserForId(request);
                //得到当前登录用户的客户资料管理权限
                Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);

                //客户信息
                List<Customer> customerList = null;
                if(loginLevel == User.LEVEL_COMPANY) {
                    customerList = findCustomerService.find();
                }else{
                    if(UserGroupResource.ISBROWSE_ME == isBrowse){
                        customerList = findCustomerService.findForMe(loginZzCode, loginId);
                    }else{
                        customerList = findCustomerService.findForChild(loginZzCode);
                    }
                }
                request.setAttribute("customerList", customerList);
            }

            //获取当前ip地址
            String ip = IpTools.getIpAddress(request);
            request.setAttribute("ip", ip);
            request.setAttribute("random", Math.random()*1000000);
        } catch (Exception e) {
            super.outputException(request, e, log, "打开新增页面");
            return "error";
        }
        return "app/addInterview";
    }

    /**
     * 新增访谈记录
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request,
                          Interview interview,
                          @RequestParam(value = "filePaths", required = false, defaultValue = "")String filePaths){
        JSONObject jsonObject = new JSONObject();
        try{
            addInterviewService.addForApp(interview, null == filePaths ? null : filePaths.split(","), request);
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "新增访谈记录");
            if(msg.indexOf("for key 'creator'") > 0){
                jsonObject.put("state", 0);
            }else {
                jsonObject.put("state", 1);
                jsonObject.put("msg", msg);
            }
        }
        return jsonObject;
    }
}
