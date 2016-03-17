package com.zs.service.customer.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerByNoDAO;
import com.zs.dao.customerlinkman.DelLinkmanByCustomerIdDAO;
import com.zs.dao.interview.DelInterviewByCustomerIdDAO;
import com.zs.domain.customer.Customer;
import com.zs.service.customer.DelCustomerByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("delCustomerByIdService")
public class DelCustomerByIdServiceImpl extends EntityServiceImpl<Customer, FindCustomerByNoDAO> implements DelCustomerByIdService {

    @Resource
    private DelInterviewByCustomerIdDAO delInterviewByCustomerIdDAO;
    @Resource
    private DelLinkmanByCustomerIdDAO delLinkmanByCustomerIdDAO;

    @Override
    @Transactional
    public void del(String... ids) throws Exception {
        if(ids == null){
            throw new BusinessException("请选择要删除的记录");
        }
        for(String id : ids){
            Customer customer = super.get(Long.parseLong(id));
            if(customer != null){
                //删除客户的访谈记录
                delInterviewByCustomerIdDAO.del(customer.getId());
                //删除客户的联系人
                delLinkmanByCustomerIdDAO.del(customer.getId());
                //删除客户信息
                super.remove(customer);
            }
        }
    }
}
