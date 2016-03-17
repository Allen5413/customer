package com.zs.service.customerstate;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerState;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerStateService extends EntityService<CustomerState> {
    public List<CustomerState> findAll()throws Exception;
}
