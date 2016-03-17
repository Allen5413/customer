package com.zs.service.customertype;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerType;

/**
 * Created by Allen on 2016/3/9.
 */
public interface AddCustomerTypeService extends EntityService<CustomerType> {
    public void add(CustomerType customerType, String zzCode)throws Exception;
}
