package com.zs.web.controller.customerstate;

import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.AddCustomerStateService;
import com.zs.service.customerstate.EditCustomerStateService;
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

/**
 * Created by Allen on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/editCustomerState")
public class EditCustomerStateController extends
        LoggerController<CustomerState, EditCustomerStateService> {

    private static Logger log = Logger.getLogger(EditCustomerStateController.class);

    @Resource
    private EditCustomerStateService editCustomerStateService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request, @RequestParam("id") long id){
        CustomerState customerState = editCustomerStateService.get(id);
        request.setAttribute("customerState", customerState);
        return "customset/customerStateEdit";
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request,
                             @RequestParam("id") long id,
                             @RequestParam("name") String name,
                             @RequestParam("state") int state,
                             @RequestParam(value = "remark", required = false, defaultValue = "") String remark){
        JSONObject jsonObject = new JSONObject();
        try{
            editCustomerStateService.edit(id, name, state, remark, UserTools.getLoginUserForZzCode(request));
            jsonObject.put("state", 0);
        }catch(Exception e){
            String msg = super.outputException(request, e, log, "编辑客户状态");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
