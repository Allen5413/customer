package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

/**
 * Created by Allen on 2016/3/8.
 */
public interface AssignCustomerService extends EntityService<Customer> {
    public void assign(long id, long userId, String zzCode)throws Exception;
}
