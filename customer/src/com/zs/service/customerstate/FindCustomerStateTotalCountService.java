package com.zs.service.customerstate;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerState;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerStateTotalCountService extends EntityService<CustomerState> {
    public List<JSONObject> find()throws Exception;
}
