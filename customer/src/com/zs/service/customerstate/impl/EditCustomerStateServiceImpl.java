package com.zs.service.customerstate.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerstate.FindCustomerStateByNameDAO;
import com.zs.dao.customerstate.FindCustomerStateDAO;
import com.zs.domain.customer.CustomerState;
import com.zs.service.customerstate.EditCustomerStateService;
import com.zs.tools.DateTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("editCustomerStateService")
public class EditCustomerStateServiceImpl extends EntityServiceImpl<CustomerState, FindCustomerStateDAO> implements EditCustomerStateService {

    @Resource
    private FindCustomerStateByNameDAO findCustomerStateByNameDAO;

    @Override
    public void edit(long id, String name, int state, String remark, String zzCode) throws Exception {
        CustomerState customerState = super.get(id);
        if(null == customerState){
            throw new BusinessException("没有找到客户状态");
        }
        if(!name.equals(customerState.getName())){
            CustomerState customerState2 = findCustomerStateByNameDAO.find(name);
            if(null != customerState2){
                throw new BusinessException("状态名称已经存在");
            }
        }
        customerState.setName(name);
        customerState.setState(state);
        customerState.setRemark(remark);
        customerState.setOperator(zzCode);
        customerState.setOperateTime(DateTools.getLongNowTime());
        super.update(customerState);
    }
}
