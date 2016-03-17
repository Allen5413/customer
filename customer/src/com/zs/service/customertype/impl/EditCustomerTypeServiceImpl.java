package com.zs.service.customertype.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customertype.FindCustomerTypeByNameDAO;
import com.zs.dao.customertype.FindCustomerTypeDAO;
import com.zs.domain.customer.CustomerType;
import com.zs.service.customertype.EditCustomerTypeService;
import com.zs.tools.DateTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("editCustomerTypeService")
public class EditCustomerTypeServiceImpl extends EntityServiceImpl<CustomerType, FindCustomerTypeDAO> implements EditCustomerTypeService {

    @Resource
    private FindCustomerTypeByNameDAO findCustomerTypeByNameDAO;

    @Override
    public void edit(long id, String name, String remark, String zzCode) throws Exception {
        CustomerType customerType = super.get(id);
        if(null == customerType){
            throw new BusinessException("没有找到客户类型");
        }
        if(!name.equals(customerType.getName())){
            CustomerType customerType2 = findCustomerTypeByNameDAO.find(name);
            if(null != customerType2){
                throw new BusinessException("类型名称已经存在");
            }
        }
        customerType.setName(name);
        customerType.setRemark(remark);
        customerType.setOperator(zzCode);
        customerType.setOperateTime(DateTools.getLongNowTime());
        super.update(customerType);
    }
}
