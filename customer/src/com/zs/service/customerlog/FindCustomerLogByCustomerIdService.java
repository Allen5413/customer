package com.zs.service.customerlog;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerLog;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindCustomerLogByCustomerIdService extends EntityService<CustomerLog> {
    public List<JSONObject> find(long customerId)throws Exception;
}
