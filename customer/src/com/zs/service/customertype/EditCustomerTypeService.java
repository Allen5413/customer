package com.zs.service.customertype;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerType;

/**
 * Created by Allen on 2016/3/9.
 */
public interface EditCustomerTypeService extends EntityService<CustomerType> {
    public void edit(long id, String name, int state, String remark, String zzCode)throws Exception;
}
