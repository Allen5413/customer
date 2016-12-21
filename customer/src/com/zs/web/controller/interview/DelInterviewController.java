package com.zs.web.controller.interview;

import com.zs.domain.customer.Interview;
import com.zs.service.interview.DelInterviewByIdService;
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
@RequestMapping(value = "/delInterview")
public class DelInterviewController extends
        LoggerController<Interview, DelInterviewByIdService> {
    private static Logger log = Logger.getLogger(DelInterviewController.class);

    @Resource
    private DelInterviewByIdService delInterviewByIdService;

    /**
     * 删除访谈记录
     * @param request
     * @return
     */
    @RequestMapping(value = "interviewDel")
    @ResponseBody
    public JSONObject interviewDel(HttpServletRequest request,
                          @RequestParam("ids")String ids){
        JSONObject jsonObject = new JSONObject();
        try{
            delInterviewByIdService.del(request.getRealPath(""), ids.split(","));
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "删除访谈记录");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}
