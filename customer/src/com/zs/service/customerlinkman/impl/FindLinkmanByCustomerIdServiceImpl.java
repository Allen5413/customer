package com.zs.service.customerlinkman.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerlinkman.FindLinkmanByCustomerIdDAO;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("findLinkmanByCustomerIdService")
public class FindLinkmanByCustomerIdServiceImpl extends EntityServiceImpl<CustomerLankman, FindLinkmanByCustomerIdDAO> implements FindLinkmanByCustomerIdService {

    @Resource
    private FindLinkmanByCustomerIdDAO findLinkmanByCustomerIdDAO;

    @Override
    public List<CustomerLankman> find(long customerId) {
        return findLinkmanByCustomerIdDAO.find(customerId);
    }
}
