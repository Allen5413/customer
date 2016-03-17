package com.zs.service.customerstate.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerstate.FindCustomerStateDAO;
import com.zs.domain.customer.CustomerState;
import com.zs.service.customer.FindCustomerByWhereService;
import com.zs.service.customerstate.DelCustomerStateByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("delCustomerStateByIdService")
public class DelCustomerStateByIdServiceImpl extends EntityServiceImpl<CustomerState, FindCustomerStateDAO> implements DelCustomerStateByIdService {

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
            CustomerState customerState = super.get(Long.parseLong(id));
            //查询是否还存在该状态的客户，存在就不能删除
            Map<String, String> params = new HashMap<String, String>();
            params.put("stateId", id);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurrentPage(1);
            pageInfo.setCountOfCurrentPage(1);
            pageInfo = findCustomerByWhereService.findPageByWhere(pageInfo, params);
            if(pageInfo.getTotalCount() > 0){
                throw new BusinessException("还存在状态["+customerState.getName()+"]的客户，不能被删除");
            }
            super.remove(customerState);
        }
    }
}
