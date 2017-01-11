package com.zs.service.customerstate.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerDAO;
import com.zs.dao.customerstate.FindCustomerStateDAO;
import com.zs.dao.customerstate.FindCustomerStateTotalCountDAO;
import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.FindCustomerStateTotalCountService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/1/9.
 */
@Service("findCustomerStateTotalCountService")
public class FindCustomerStateTotalCountServiceImpl extends EntityServiceImpl<CustomerState, FindCustomerStateDAO>
        implements FindCustomerStateTotalCountService {

    @Resource
    private FindCustomerStateTotalCountDAO findCustomerStateTotalCountDAO;
    @Resource
    private FindCustomerDAO findCustomerDAO;

    @Override
    public List<JSONObject> find() throws Exception {
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        List<Object[]> list = findCustomerStateTotalCountDAO.find();
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
