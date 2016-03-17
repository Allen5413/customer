package com.zs.service.customertype.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customertype.FindCustomerTypeByNameDAO;
import com.zs.dao.customertype.FindCustomerTypeDAO;
import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.AddCustomerTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("addCustomerTypeService")
public class AddCustomerTypeServiceImpl extends EntityServiceImpl<CustomerType, FindCustomerTypeDAO> implements AddCustomerTypeService {

    @Resource
    private FindCustomerTypeByNameDAO findCustomerTypeByNameDAO;

    @Override
    public void add(CustomerType customerType, String zzCode) throws Exception {
        CustomerType customerType2 = findCustomerTypeByNameDAO.find(customerType.getName());
        if(null != customerType2){
            throw new BusinessException("该客户类型已经存在");
        }
        customerType.setCreator(zzCode);
        customerType.setOperator(zzCode);
        super.save(customerType);
    }
}
