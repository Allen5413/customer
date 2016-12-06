package com.zs.service.customerlinkman;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.CustomerLankman;

/**
 * Created by Allen on 2016/3/8.
 */
public interface DelLinkmanByIdService extends EntityService<CustomerLankman> {
    public void del(long id, String loginName);
}
