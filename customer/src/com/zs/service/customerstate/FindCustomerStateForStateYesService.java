package com.zs.service.customerstate;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerState;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindCustomerStateForStateYesService extends EntityService<CustomerState> {
    public List<CustomerState> find();
}
