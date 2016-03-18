package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindCustomerService extends EntityService<Customer> {
    public List<Customer> findForOrderByName();
}
