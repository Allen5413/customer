package com.zs.service.customerstate;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerState;

/**
 * Created by Allen on 2016/3/9.
 */
public interface EditCustomerStateService extends EntityService<CustomerState> {
    public void edit(long id, String name, String remark, String zzCode)throws Exception;
}
