package com.zs.web.controller.interview.app;

import com.zs.domain.customer.Interview;
import com.zs.service.customerstate.FindCustomerStateTotalCountService;
import com.zs.service.customertype.FindCustomerTypeTotalCountService;
import com.zs.service.interview.FindInterviewTotalService;
import com.zs.tools.DateTools;
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

/**
 * Created by Allen on 2016/12/29.
 */
@Controller
@RequestMapping(value = "/findInterviewTotalForApp")
public class FindInterviewTotalForAppController extends
        LoggerController<Interview, FindInterviewTotalService> {
    private static Logger log = Logger.getLogger(AddInterviewForAppController.class);

    @Resource
    private FindInterviewTotalService findInterviewTotalService;
    @Resource
    private FindCustomerStateTotalCountService findCustomerStateTotalCountService;
    @Resource
    private FindCustomerTypeTotalCountService findCustomerTypeTotalCountService;

    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
            @RequestParam(value = "year", required = false)Integer year,
            @RequestParam(value = "showYear", required = false)Integer showYear,
            @RequestParam(value = "userCountYear", required = false)Integer userCountYear,
            @RequestParam(value = "month", required = false)Integer month,
            @RequestParam(value = "num", required = false, defaultValue = "5")int num,
            @RequestParam(value = "flag", required = false, defaultValue = "0")String flag) {
        try {
            if(null == userCountYear){
                userCountYear = Integer.parseInt(DateTools.getThisYear());
            }
            if(null == month){
                month = Integer.parseInt(DateTools.getThisMonth());
            }
            int thisYear = Integer.parseInt(DateTools.getThisYear());
            int[] yearList = {thisYear, thisYear-1, thisYear-2, thisYear-3, thisYear-4, thisYear-5, thisYear-6, thisYear-7, thisYear-8, thisYear-9};
            request.setAttribute("json", findInterviewTotalService.findTotal());
            request.setAttribute("json2", findInterviewTotalService.findYear(year));
            request.setAttribute("csCountList", findCustomerStateTotalCountService.find());
            request.setAttribute("ctCountList", findCustomerTypeTotalCountService.find());
            request.setAttribute("useCountList", findInterviewTotalService.findInterviewForUserCount(userCountYear, month, num));
            request.setAttribute("yearList", yearList);
            request.setAttribute("year", year);
            request.setAttribute("showYear", null == year ? Integer.parseInt(DateTools.getThisYear()) : year);
            request.setAttribute("userCountYear", userCountYear);
            request.setAttribute("month", month);
            request.setAttribute("num", num);
            request.setAttribute("flag", flag);
            request.setAttribute("month12", DateTools.getLast12Months());
            return "app/interviewTotal";
        }catch (Exception e){
            return "error";
        }
    }
}