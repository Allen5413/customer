package com.zs.service.customer.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerDAO;
import com.zs.domain.customer.Customer;
import com.zs.service.customer.FindCustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
@Service("findCustomerService")
public class FindCustomerServiceImpl extends EntityServiceImpl<Customer, FindCustomerDAO> implements FindCustomerService {

    @Resource
    private FindCustomerDAO findCustomerDAO;

    @Override
    public List<Customer> findForOrderByName() {
        return findCustomerDAO.findForOrderByName();
    }
}
