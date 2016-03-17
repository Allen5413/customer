package com.zs.service.customerlinkman;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerLankman;

import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
public interface FindLinkmanByCustomerIdService extends EntityService<CustomerLankman> {
    public List<CustomerLankman> find(long customerId);
}
