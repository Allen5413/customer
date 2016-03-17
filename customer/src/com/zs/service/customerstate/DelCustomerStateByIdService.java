package com.zs.service.customerstate;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerState;

/**
 * Created by Allen on 2016/3/9.
 */
public interface DelCustomerStateByIdService extends EntityService<CustomerState> {
    public void del(String... ids)throws Exception;
}
