package com.zs.service.customerstate.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerstate.FindCustomerStateByNameDAO;
import com.zs.dao.customerstate.FindCustomerStateDAO;
import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.AddCustomerStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("addCustomerStateService")
public class AddCustomerStateServiceImpl extends EntityServiceImpl<CustomerState, FindCustomerStateDAO> implements AddCustomerStateService {

    @Resource
    private FindCustomerStateByNameDAO findCustomerStateByNameDAO;

    @Override
    public void add(CustomerState customerState, String zzCode) throws Exception {
        CustomerState customerState1 = findCustomerStateByNameDAO.find(customerState.getName());
        if(null != customerState1){
            throw new BusinessException("该客户状态已经存在");
        }
        customerState.setCreator(zzCode);
        customerState.setOperator(zzCode);
        super.save(customerState);
    }
}
