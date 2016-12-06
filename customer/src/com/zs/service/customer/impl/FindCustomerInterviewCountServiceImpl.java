package com.zs.service.customer.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerInterviewCountDAO;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import com.zs.domain.customer.Customer;
import com.zs.service.customer.FindCustomerInterviewCountService;
import com.zs.tools.UserTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/6/6.
 */
@Service("findCustomerInterviewCountService")
public class FindCustomerInterviewCountServiceImpl extends EntityServiceImpl<Customer, FindCustomerInterviewCountDAO> implements FindCustomerInterviewCountService {

    @Resource
    private FindCustomerInterviewCountDAO findCustomerInterviewCountDAO;

    @Override
    public List<Map<String, String>> find(HttpServletRequest request) {

        int loginLevel = UserTools.getLoginUserForLevel(request);
        String loginZzCode = UserTools.getLoginUserForZzCode(request);
        long loginId = UserTools.getLoginUserForId(request);

        //得到当前登录用户的客户资料管理权限
        Integer isBrowse = UserTools.getLoginUserForIsBrowse(request);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<Object[]> findList = null;

        if(loginLevel > User.LEVEL_COMPANY) {
            if(isBrowse == UserGroupResource.ISBROWSE_ME){
                findList = findCustomerInterviewCountDAO.findForMe(loginZzCode, loginId);
            }else{
                findList = findCustomerInterviewCountDAO.findForChild("%" + loginZzCode + "%");
            }
        }else{
            findList = findCustomerInterviewCountDAO.find();
        }
        if(null != findList && 0 < findList.size()){
            for(Object[] objs : findList){
                Map<String, String> map = new HashMap<String, String>();
                String id = objs[0].toString();
                String name = objs[1].toString();
                String count = objs[2].toString();
                String date = objs[3].toString();

                map.put("id", id);
                map.put("name", name);
                map.put("count", count);
                map.put("date", date);

                list.add(map);
            }
        }
        return list;
    }
}
