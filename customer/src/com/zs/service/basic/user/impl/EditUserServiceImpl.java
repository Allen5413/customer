package com.zs.service.basic.user.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.dao.basic.usergroupuser.FindUserGroupUserByUserIdDAO;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.user.EditUserService;
import com.zs.tools.DateTools;
import com.zs.tools.HttpRequestTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 实现修改用户接口
 * Created by Allen on 2015/4/27.
 */
@Service("editUserService")
public class EditUserServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements EditUserService {

    @Resource
    private FindUserByZZDAO findUserByZZDAO;
    @Resource
    private FindUserGroupUserByUserIdDAO findUserGroupUserByUserIdDAO;

    @Override
    public void editUser(User user, long userGroupId, String zzCode) throws Exception {
        if(null != user){
            //通过id查询User
            User oldUser = findUserByZZDAO.get(user.getId());
            //验证ZZ号是否已经存在
            User validUser = findUserByZZDAO.find(user.getZzCode());
            if(null != validUser && !validUser.getZzCode().equals(oldUser.getZzCode())){
                throw new BusinessException("ZZ已经存在");
            }
            //验证zz的真实性
            boolean isExistsZZ = HttpRequestTools.isExistsZZ(user.getZzCode());
            if(!isExistsZZ){
                throw new BusinessException("ZZ不存在");
            }
            oldUser.setZzCode(user.getName());
            oldUser.setName(user.getName());
            oldUser.setPhone(user.getPhone());
            oldUser.setState(user.getState());
            oldUser.setRemark(user.getRemark());
            oldUser.setOperator(zzCode);
            oldUser.setOperateTime(DateTools.getLongNowTime());
            super.update(oldUser);

            //修改用户和角色的关联
            UserGroupUser userGroupUser = findUserGroupUserByUserIdDAO.find(user.getId());
            userGroupUser.setUserGroupId(userGroupId);
            userGroupUser.setCreator(zzCode);
            findUserGroupUserByUserIdDAO.update(userGroupUser);
        }
    }
}
