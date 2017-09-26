package com.zs.web.controller.attop;

import com.zs.tools.DateTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Allen on 2017/3/29.
 */
@Controller
@RequestMapping(value = "/attopRegUserTotal")
public class FindRegUserTotalController extends LoggerController {
    private static Logger log = Logger.getLogger(FindRegUserTotalController.class);


    @RequestMapping(value = "find")
    public String find(HttpServletRequest request){
        try{
            request.setAttribute("days", this.getDistanceDays());
            return "attop/regUserTotal";
        }
        catch(Exception e){
            super.outputException(request, e, log, "查询注册用户统计信息失败");
            return "error";
        }
    }

    @RequestMapping(value = "findDays")
    @ResponseBody
    public JSONObject addUserGroup(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("days", this.getDistanceDays());
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 两个时间之间相差距离多少天
     * @return 相差天数
     */
    private char[] getDistanceDays() throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long days=0;
        Date one = df.parse(DateTools.getShortNowTime().toString());
        Date two = df.parse("2017-05-31");
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = diff / (1000 * 60 * 60 * 24);
        return (days+"").toCharArray();
    }
}
