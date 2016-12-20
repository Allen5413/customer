package com.zs.service.customerlog.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.cusotmerlog.CustomerLogDAO;
import com.zs.domain.customer.CustomerLog;
import com.zs.service.customerlog.FindCustomerLogByCustomerIdService;
import com.zs.tools.DateJsonValueProcessorTools;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
@Service("findCustomerLogByCustomerIdService")
public class FindCustomerLogByCustomerIdServiceImpl extends EntityServiceImpl<CustomerLog, CustomerLogDAO>
    implements FindCustomerLogByCustomerIdService{

    @Resource
    private CustomerLogDAO  customerLogDAO;

    @Override
    @Transactional
    public List<JSONObject> find(long customerId) throws Exception {
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        List<Object[]> list = customerLogDAO.findByCustomerId(customerId);
        if(null != list && 0 < list.size()){
            for(Object[] objs : list){
                //JSON日期转换必须要传bean，所以这里先把日期format后在设置值
                CustomerLog customerLog = new CustomerLog();
                customerLog.setOperateTime((Date) objs[10]);
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessorTools());
                JSONObject jsonObject = JSONObject.fromObject(customerLog, jsonConfig);
                jsonObject.put("operateTime", "null".equals(jsonObject.get("operateTime").toString()) ? "" : jsonObject.get("operateTime"));
                jsonObject.put("no", null == objs[0] ? "" : objs[0]);
                jsonObject.put("code", null == objs[1] ? "" : objs[1]);
                jsonObject.put("name", null == objs[2] ? "" : objs[2]);
                jsonObject.put("scale", null == objs[3] ? "" : objs[3]);
                jsonObject.put("aName", null == objs[4] ? "" : objs[4]);
                jsonObject.put("address", null == objs[5] ? "" : objs[5]);
                jsonObject.put("tName", null == objs[6] ? "" : objs[6]);
                jsonObject.put("sName", null == objs[7] ? "" : objs[7]);
                jsonObject.put("remark", null == objs[8] ? "" : objs[8]);
                jsonObject.put("operatorName", null == objs[9] ? "" : objs[9]);
                resultList.add(jsonObject);
            }
        }
        return resultList;
    }
}
