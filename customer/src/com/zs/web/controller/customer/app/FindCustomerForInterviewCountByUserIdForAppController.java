package com.zs.web.controller.customer.app;

import com.zs.dao.basic.user.*;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.customer.FindCustomerForInterviewCountByUserIdService;
import com.zs.tools.UserTools;
import com.zs.web.controller.LoggerController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/7.
 */
@Controller
@RequestMapping(value = "/findCustomerForInterviewCountByUserIdForApp")
public class FindCustomerForInterviewCountByUserIdForAppController extends LoggerController {
    private static Logger log = Logger.getLogger(FindCustomerForInterviewCountByUserIdForAppController.class);

    @Resource
    private FindCustomerForInterviewCountByUserIdService findCustomerForInterviewCountByUserIdService;
    @Resource
    private FindUserByParenSignDAO findUserByParenSignDAO;
    @Resource
    private FindUserForAreaDAO findUserForAreaDAO;
    @Resource
    private FindUserForProvDAO findUserForProvDAO;
    @Resource
    private FindUserForBusinessDAO findUserForBusinessDAO;


    @RequestMapping(value = "find")
    public String find(@RequestParam(value="userId", required=false, defaultValue="") String userId,
                       @RequestParam(value="flag", required=false, defaultValue="") String flag,
                       @RequestParam(value="areaId", required=false, defaultValue="") String areaId,
                       @RequestParam(value="provId", required=false, defaultValue="") String provId,
                       @RequestParam(value="businessId", required=false, defaultValue="") String businessId,
                       HttpServletRequest request){
        try{
            String loginZzCode = UserTools.getLoginUserForZzCode(request);
            int loginLevel = UserTools.getLoginUserForLevel(request);
            long loginId = UserTools.getLoginUserForId(request);

            //得到当前登录用户的客户资料管理权限
            Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);

            if(null == isBrowse){
                return "app/findCustomerForInterviewCountByUserId";
            }
            if(UserGroupResource.ISBROWSE_ME == isBrowse){
                request.setAttribute("userName", UserTools.getLoginUserForName(request));
            }


            List<User> areaUserList = null;
            List<User> provUserList = null;
            List<User> businessUserList = null;
            if (loginLevel == User.LEVEL_COMPANY) {
                if(StringUtils.isEmpty(userId)) {
                    areaUserList = findUserForAreaDAO.find();
                    provUserList = findUserForProvDAO.find();
                    businessUserList = findUserForBusinessDAO.find();
                }else{
                    User changeUser = findUserByParenSignDAO.get(Long.parseLong(userId));
                    if("1".equals(flag)) {
                        areaUserList = findUserForAreaDAO.find();
                        provUserList = findUserByParenSignDAO.findAndLevel("%" + changeUser.getZzCode() + "%", User.LEVEL_PROV);
                        businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                    }
                    if("2".equals(flag)) {
                        areaUserList = findUserForAreaDAO.find();
                        if(StringUtils.isEmpty(areaId)) {
                            provUserList = findUserForProvDAO.find();
                        }else{
                            User changeAreaUser = findUserByParenSignDAO.get(Long.parseLong(areaId));
                            provUserList = findUserByParenSignDAO.findAndLevel("%" + changeAreaUser.getZzCode() + "%", User.LEVEL_PROV);
                        }
                        businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                    }
                    if("3".equals(flag)) {
                        areaUserList = findUserForAreaDAO.find();
                        provUserList = findUserForProvDAO.find();
                        if(StringUtils.isEmpty(areaId) && StringUtils.isEmpty(provId)) {
                            businessUserList = findUserForBusinessDAO.find();
                        }else{
                            if(!StringUtils.isEmpty(provId)) {
                                User changeProvUser = findUserByParenSignDAO.get(Long.parseLong(provId));
                                businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeProvUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                            }else{
                                if(!StringUtils.isEmpty(areaId)) {
                                    User changeAreaUser = findUserByParenSignDAO.get(Long.parseLong(areaId));
                                    businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeAreaUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                                }
                            }
                        }

                    }
                }
            }
            if (loginLevel == User.LEVEL_AREA) {
                if(StringUtils.isEmpty(userId)) {
                    provUserList = findUserByParenSignDAO.findAndLevel("%" + loginZzCode + "%", User.LEVEL_PROV);
                    businessUserList = findUserByParenSignDAO.findAndLevel("%" + loginZzCode + "%", User.LEVEL_BUSINESS);
                }else{
                    User changeUser = findUserByParenSignDAO.get(Long.parseLong(userId));
                    if("2".equals(flag)) {
                        provUserList = findUserByParenSignDAO.findAndLevel("%" + loginZzCode + "%", User.LEVEL_PROV);
                        businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                    }
                    if("3".equals(flag)) {
                        provUserList = findUserByParenSignDAO.findAndLevel("%" + loginZzCode + "%", User.LEVEL_PROV);
                        if(!StringUtils.isEmpty(provId)) {
                            User changeProvUser = findUserByParenSignDAO.get(Long.parseLong(provId));
                            businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeProvUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                        }else{
                            businessUserList = findUserByParenSignDAO.findAndLevel("%" + changeUser.getZzCode() + "%", User.LEVEL_BUSINESS);
                        }
                    }
                }
            }
            if (loginLevel == User.LEVEL_PROV) {
                businessUserList = findUserByParenSignDAO.findAndLevel("%" + loginZzCode + "%", User.LEVEL_BUSINESS);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("loginLevel", loginLevel+"");
            params.put("isBrowse", isBrowse+"");
            params.put("loginZzCode", loginZzCode);
            params.put("loginId", loginId+"");
            List<JSONObject> list = findCustomerForInterviewCountByUserIdService.find(params);

            request.setAttribute("loginLevel", loginLevel);
            request.setAttribute("list", list);
            request.setAttribute("areaUserList", areaUserList);
            request.setAttribute("provUserList", provUserList);
            request.setAttribute("businessUserList", businessUserList);
            return "app/findCustomerForInterviewCountByUserId";
        }
        catch(Exception e){
            super.outputException(request, e, log, "分页查询客户信息");
            return "error";
        }
    }
}
