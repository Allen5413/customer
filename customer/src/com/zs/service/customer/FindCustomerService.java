package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindCustomerService extends EntityService<Customer> {
    public List<Customer> find();

    public List<Customer> findForChild(String zzCode);

    public List<Customer> findForMe(String zzCode, long userId);
}
