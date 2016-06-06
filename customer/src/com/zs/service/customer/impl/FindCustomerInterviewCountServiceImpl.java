package com.zs.service.customer.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerInterviewCountDAO;
import com.zs.domain.customer.Customer;
import com.zs.service.customer.FindCustomerInterviewCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Map<String, String>> find() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<Object[]> findList = findCustomerInterviewCountDAO.find();
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
