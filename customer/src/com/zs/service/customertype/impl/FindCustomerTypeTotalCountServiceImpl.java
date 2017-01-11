package com.zs.service.customertype.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerDAO;
import com.zs.dao.customertype.FindCustomerTypeDAO;
import com.zs.dao.customertype.FindCustomerTypeTotalCountDAO;
import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.FindCustomerTypeTotalCountService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/1/9.
 */
@Service("findCustomerTypeTotalCountService")
public class FindCustomerTypeTotalCountServiceImpl extends EntityServiceImpl<CustomerType, FindCustomerTypeDAO>
        implements FindCustomerTypeTotalCountService {

    @Resource
    private FindCustomerTypeTotalCountDAO findCustomerTypeTotalCountDAO;
    @Resource
    private FindCustomerDAO findCustomerDAO;

    @Override
    public List<JSONObject> find() throws Exception {
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        List<Object[]> list = findCustomerTypeTotalCountDAO.find();
        BigDecimal totalCount = new BigDecimal(findCustomerDAO.getAll().size());
        for(Object[] objs : list){
            JSONObject json = new JSONObject();
            int count = Integer.parseInt(objs[1].toString());
            json.put("name", objs[0]+"("+count+")");
            BigDecimal b = new BigDecimal(count).multiply(new BigDecimal(100));
            json.put("countPoint", b.divide(totalCount, 2, BigDecimal.ROUND_HALF_UP));
            resultList.add(json);
        }
        return resultList;
    }
}
