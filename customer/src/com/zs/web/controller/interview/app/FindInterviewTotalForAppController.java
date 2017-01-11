package com.zs.web.controller.interview.app;

import com.zs.domain.customer.Interview;
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

    @RequestMapping(value = "open")
    public String open(HttpServletRequest request) {
        try {
            int thisYear = Integer.parseInt(DateTools.getThisYear());
            int[] yearList = {thisYear, thisYear-1, thisYear-2, thisYear-3, thisYear-4, thisYear-5, thisYear-6, thisYear-7, thisYear-8, thisYear-9};
            request.setAttribute("json", findInterviewTotalService.findTotal());
            request.setAttribute("yearList", yearList);
            request.setAttribute("year", DateTools.getThisYear());
            request.setAttribute("month", DateTools.getThisMonth());
            request.setAttribute("month12", DateTools.getLast12Months());
            return "app/interviewTotal";
        }catch (Exception e){
            return "error";
        }
    }

    @RequestMapping(value = "findYear")
    @ResponseBody
    public JSONObject findYear(HttpServletRequest request,
                               @RequestParam(value = "year", required = false)Integer year){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("json", findInterviewTotalService.findYear(year));
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "统计图");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }

    @RequestMapping(value = "findUserCount")
    @ResponseBody
    public JSONObject findUserCount(HttpServletRequest request,
                               @RequestParam(value = "year", required = false, defaultValue = "")String year,
                               @RequestParam(value = "month", required = false, defaultValue = "")String month,
                               @RequestParam(value = "num", required = false, defaultValue = "5")int num){
        JSONObject jsonObject = new JSONObject();
        try{
            if(StringUtils.isEmpty(year)){
                year = DateTools.getThisYear();
            }
            if(StringUtils.isEmpty(month)){
                month = DateTools.getThisMonth();
            }
            jsonObject.put("list", findInterviewTotalService.findInterviewForUserCount(Integer.parseInt(year), Integer.parseInt(month), num));
            jsonObject.put("state", 0);
        }
        catch(Exception e){
            String msg = super.outputException(request, e, log, "统计排行");
            jsonObject.put("state", 1);
            jsonObject.put("msg", msg);
        }
        return jsonObject;
    }
}