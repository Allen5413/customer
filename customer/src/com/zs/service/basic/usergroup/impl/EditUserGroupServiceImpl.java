package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByNameDAO;
import com.zs.dao.basic.usergroup.UserGroupDAO;
import com.zs.dao.basic.usergroupresource.DelUserGroupResourceByGroupIdDAO;
import com.zs.dao.basic.usergroupresource.UserGroupResourceDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.usergroup.EditUserGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 实现修改用户组接口
 * Created by Allen on 2015/4/27.
 */
@Service("editUserGroupService")
public class EditUserGroupServiceImpl extends EntityServiceImpl<UserGroup, UserGroupDAO> implements EditUserGroupService {

    @Resource
    private FindUserGroupByNameDAO findUserGroupByNameDAO;
    @Resource
    private UserGroupDAO userGroupDAO;
    @Resource
    private DelUserGroupResourceByGroupIdDAO delUserGroupResourceByGroupIdDAO;
    @Resource
    private UserGroupResourceDAO userGroupResourceDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void editUserGroup(UserGroup userGroup, String resourceIds, String loginName) throws Exception {
        if(null != userGroup) {
            //是否存在
            UserGroup isExistsUserGroup = super.get(userGroup.getId());
            if(null == isExistsUserGroup){
                throw new BusinessException("用户组不存在！");
            }
            //查询用户组名称是否已经存在
            UserGroup validUserGroup = findUserGroupByNameDAO.getUserGroupByName(userGroup.getName());
            if(null != validUserGroup && validUserGroup.getName().equals(userGroup.getName()) && validUserGroup.getId() != userGroup.getId()){
                throw new BusinessException("用户组名称已经存在！");
            }
            //删掉之前用户组关联得资源，重新添加关联的资源
            delUserGroupResourceByGroupIdDAO.delUserGroupResourceByGroupId(userGroup.getId());
            //重新关联资源
            if(!StringUtils.isEmpty(resourceIds)){
                String[] resourceIdArray = resourceIds.split(",");
                if(null != resourceIdArray && 0 < resourceIdArray.length){
                    for(String resourceId : resourceIdArray){
                        UserGroupResource userGroupResource = new UserGroupResource();
                        userGroupResource.setUserGroupId(userGroup.getId());
                        userGroupResource.setResourceId(Long.parseLong(resourceId));
                        userGroupResource.setCreator(loginName);
                        userGroupResourceDAO.save(userGroupResource);
                    }
                }
            }
            //修改用户组基本信息
            userGroup.setOperator(loginName);
            userGroup.setCreator(isExistsUserGroup.getCreator());
            userGroup.setCreateTime(isExistsUserGroup.getCreateTime());
            userGroupDAO.update(userGroup);
        }
    }
}
