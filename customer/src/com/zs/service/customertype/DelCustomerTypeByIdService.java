package com.zs.service.customertype;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerType;

/**
 * Created by Allen on 2016/3/9.
 */
public interface DelCustomerTypeByIdService extends EntityService<CustomerType> {
    public void del(String... ids) throws Exception;
}
