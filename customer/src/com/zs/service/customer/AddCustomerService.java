package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/7.
 */
public interface AddCustomerService extends EntityService<Customer> {
    public void add(Customer customer, String linkmanInfo, HttpServletRequest request)throws Exception;
}
