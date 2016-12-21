package com.zs.web.controller.interviewfile;

import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.Interview;
import com.zs.domain.customer.InterviewFile;
import com.zs.service.customer.AddCustomerService;
import com.zs.service.customer.EditCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.interview.EditInterviewService;
import com.zs.service.interviewfile.FindInterviewFileByInterviewIdService;
import com.zs.tools.HttpRequestTools;
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
 * Created by Allen on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/findInterviewFileByInterviewId")
public class FindInterviewFileByInterviewIdController extends
        LoggerController<Customer, EditCustomerService> {
    private static Logger log = Logger.getLogger(FindInterviewFileByInterviewIdController.class);

    @Resource
    private FindInterviewFileByInterviewIdService findInterviewFileByInterviewIdService;

    /**
     * 打开页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("interviewId")long interviewId){
        try {
            List<InterviewFile> interviewFileList = findInterviewFileByInterviewIdService.find(interviewId);
            request.setAttribute("interviewFileList", interviewFileList);

        } catch (Exception e) {
            super.outputException(request, e, log, "打开页面");
            return "error";
        }
        return "interview/interviewFile";
    }
}
