package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByNameDAO;
import com.zs.dao.basic.usergroup.UserGroupDAO;
import com.zs.dao.basic.usergroupresource.UserGroupResourceDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.usergroup.AddUserGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 实现新增用户组接口
 * Created by Allen on 2015/4/27.
 */
@Service("addUserGroupService")
public class AddUserGroupServiceImpl extends EntityServiceImpl<UserGroup, UserGroupDAO> implements AddUserGroupService {

    @Resource
    private FindUserGroupByNameDAO findUserGroupByNameDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addUserGroup(UserGroup userGroup, String zzCode) throws Exception {
        if(null != userGroup){
            //查询用户组名称是否已经存在
            UserGroup validUserGroup = findUserGroupByNameDAO.getUserGroupByName(userGroup.getName());
            if(null != validUserGroup && validUserGroup.getName().equals(userGroup.getName())){
                throw new BusinessException("角色名称已经存在！");
            }
            //添加用户组
            userGroup.setCreator(zzCode);
            userGroup.setOperator(zzCode);
            super.save(userGroup);
        }
    }
}
