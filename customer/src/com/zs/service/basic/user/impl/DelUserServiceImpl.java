package com.zs.service.basic.user.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.dao.customer.FindCustomerByUserIdDAO;
import com.zs.domain.basic.User;
import com.zs.domain.customer.Customer;
import com.zs.service.basic.user.DelUserService;
import com.zs.tools.DateTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 删除用户
 * Created by Allen on 2016/3/4.
 */
@Service("delUserService")
public class DelUserServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements DelUserService {

    @Resource
    private FindCustomerByUserIdDAO findCustomerByUserIdDAO;

    @Override
    @Transactional
    public void del(String zzCode, String... ids) throws Exception {
        if(null == ids){
            throw new BusinessException("请选择要删除的用户");
        }
        for(String id : ids) {
            User user = super.get(Long.parseLong(id));

            //查询该用户下面还有没有关联的客户，如果有，不允许删除
            List<Customer> customerList = findCustomerByUserIdDAO.find(Long.parseLong(id));
            if (customerList != null && 0 < customerList.size()) {
                throw new BusinessException("用户["+user.getZzCode()+" - "+user.getName()+"]下面还指派有客户，请取消指派后再删除！");
            }


            user.setState(User.STATE_DELETE);
            user.setOperator(zzCode);
            user.setOperateTime(DateTools.getLongNowTime());
            super.update(user);
        }
    }
}
