package com.zs.service.customertype;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerType;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerTypeTotalCountService extends EntityService<CustomerType> {
    public List<JSONObject> find()throws Exception;
}
