package com.zs.service.customertype.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customertype.FindCustomerTypeDAO;
import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.FindCustomerTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
@Service("findCustomerTypeService")
public class FindCustomerTypeServiceImpl extends EntityServiceImpl<CustomerType, FindCustomerTypeDAO> implements FindCustomerTypeService {

    @Resource
    private FindCustomerTypeDAO findCustomerTypeDAO;

    @Override
    public List<CustomerType> findAll() throws Exception {
        return findCustomerTypeDAO.findAll();
    }
}
