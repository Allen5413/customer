package com.zs.service.customertype.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customertype.FindCustomerTypeForStateYesDAO;
import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.FindCustomerTypeForStateYesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
@Service("findCustomerTypeForStateYesService")
public class FindCustomerTypeForStateYesServiceImpl extends EntityServiceImpl<CustomerType, FindCustomerTypeForStateYesDAO> implements FindCustomerTypeForStateYesService {

    @Resource
    private FindCustomerTypeForStateYesDAO findCustomerTypeForStateYesDAO;

    @Override
    public List<CustomerType> find() {
        return findCustomerTypeForStateYesDAO.find();
    }
}
