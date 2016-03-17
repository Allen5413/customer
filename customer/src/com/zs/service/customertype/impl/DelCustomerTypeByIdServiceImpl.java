package com.zs.service.customertype.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customertype.FindCustomerTypeDAO;
import com.zs.domain.customer.CustomerType;
import com.zs.service.customer.FindCustomerByWhereService;
import com.zs.service.customertype.DelCustomerTypeByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("delCustomerTypeByIdService")
public class DelCustomerTypeByIdServiceImpl extends EntityServiceImpl<CustomerType, FindCustomerTypeDAO> implements DelCustomerTypeByIdService {

    @Resource
    private FindCustomerByWhereService findCustomerByWhereService;

    @Override
    @Transactional
    public void del(String... ids) throws Exception {
        if(null == ids){
            throw new BusinessException("请选择要删除的数据");
        }
        for(String id : ids){
            //查询状态
            CustomerType customerType = super.get(Long.parseLong(id));
            //查询是否还存在该类型的客户，存在就不能删除
            Map<String, String> params = new HashMap<String, String>();
            params.put("typeId", id);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurrentPage(1);
            pageInfo.setCountOfCurrentPage(1);
            pageInfo = findCustomerByWhereService.findPageByWhere(pageInfo, params);
            if(pageInfo.getTotalCount() > 0){
                throw new BusinessException("还存在类型["+customerType.getName()+"]的客户，不能被删除");
            }
            super.remove(customerType);
        }
    }
}
