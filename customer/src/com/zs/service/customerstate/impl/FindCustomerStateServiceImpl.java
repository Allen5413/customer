package com.zs.service.customerstate.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerstate.FindCustomerStateDAO;
import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.FindCustomerStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
@Service("findCustomerStateService")
public class FindCustomerStateServiceImpl extends EntityServiceImpl<CustomerState, FindCustomerStateDAO> implements FindCustomerStateService {

    @Resource
    private FindCustomerStateDAO findCustomerStateDAO;

    @Override
    public List<CustomerState> findAll() throws Exception {
        return findCustomerStateDAO.findAll();
    }
}
