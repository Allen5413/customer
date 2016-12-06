package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/6/6.
 */
public interface FindCustomerInterviewCountService extends EntityService<Customer> {
    public List<Map<String, String>> find(HttpServletRequest request);
}
