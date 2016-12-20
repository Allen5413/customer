package com.zs.service.customerlinkman.impl;

import com.alibaba.fastjson.JSONObject;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerlinkman.FindLinkmanByCustomerIdDAO;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("findLinkmanByCustomerIdService")
public class FindLinkmanByCustomerIdServiceImpl extends EntityServiceImpl<CustomerLankman, FindLinkmanByCustomerIdDAO> implements FindLinkmanByCustomerIdService {

    @Resource
    private FindLinkmanByCustomerIdDAO findLinkmanByCustomerIdDAO;

    @Override
    public List<CustomerLankman> find(long customerId) {
        return findLinkmanByCustomerIdDAO.find(customerId);
    }

    @Override
    public List<JSONObject> findForInterviewCount(long customerId) {
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        List<Object[]> list = findLinkmanByCustomerIdDAO.findForInterviewCount(customerId);
        if(null != list && 0 < list.size()){
            for(Object[] objs : list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", null == objs[0] ? "" : objs[0]);
                jsonObject.put("name", null == objs[1] ? "" : objs[1]);
                jsonObject.put("phone", null == objs[2] ? "" : objs[2]);
                jsonObject.put("post", null == objs[3] ? "" : objs[3]);
                jsonObject.put("qq", null == objs[4] ? "" : objs[4]);
                jsonObject.put("trait", null == objs[5] ? "" : objs[5]);
                jsonObject.put("remark", null == objs[6] ? "" : objs[6]);
                jsonObject.put("interviewCount", null == objs[7] ? "" : objs[7]);
                resultList.add(jsonObject);
            }
        }
        return resultList;
    }
}
