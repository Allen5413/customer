package com.zs.service.customer.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerByUserIdDAO;
import com.zs.domain.customer.Customer;
import com.zs.service.customer.AssignCustomerService;
import com.zs.tools.DateTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户指派经理
 * Created by Allen on 2016/3/8.
 */
@Service("assignCustomerService")
public class AssignCustomerServiceImpl extends EntityServiceImpl<Customer, FindCustomerByUserIdDAO> implements AssignCustomerService {

    @Override
    @Transactional
    public void assign(long id, long userId, String zzCode) throws Exception {
        Customer customer = super.get(id);
        if(null == customer){
            throw new BusinessException("没有提交客户信息");
        }
        customer.setUserId(userId);
        customer.setOperator(zzCode);
        customer.setOperateTime(DateTools.getLongNowTime());
        super.update(customer);
    }
}