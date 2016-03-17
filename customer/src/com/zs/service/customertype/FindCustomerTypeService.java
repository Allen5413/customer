package com.zs.service.customertype;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerType;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerTypeService extends EntityService<CustomerType> {
    public List<CustomerType> findAll()throws Exception;
}
