package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

/**
 * Created by Allen on 2016/3/9.
 */
public interface DelCustomerByIdService extends EntityService<Customer> {
    public void del(String... ids)throws Exception;
}
