package com.zs.service.customerstate.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerstate.FindCustomerStateForStateYesDAO;
import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.FindCustomerStateForStateYesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
@Service("findCustomerStateForStateYesService")
public class FindCustomerStateForStateYesServiceImpl extends EntityServiceImpl<CustomerState, FindCustomerStateForStateYesDAO>
        implements FindCustomerStateForStateYesService {

    @Resource
    private FindCustomerStateForStateYesDAO findCustomerStateForStateYesDAO;

    @Override
    public List<CustomerState> find() {
        return findCustomerStateForStateYesDAO.find();
    }
}
