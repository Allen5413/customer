package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.UserGroupDAO;
import com.zs.dao.basic.usergroupresource.DelUserGroupResourceByGroupIdDAO;
import com.zs.dao.basic.usergroupuser.FindUserGroupUserByGroupIdDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.usergroup.DelUserGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实现删除用户组接口
 * Created by Allen on 2015/4/27.
 */
@Service("delUserGroupService")
public class DelUserGroupServiceImpl extends EntityServiceImpl<UserGroup, UserGroupDAO> implements DelUserGroupService {

    @Resource
    private DelUserGroupResourceByGroupIdDAO delUserGroupResourceByGroupIdDAO;
    @Resource
    private FindUserGroupUserByGroupIdDAO findUserGroupUserByGroupIdDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void delUserGroup(String... ids) throws Exception {
        for(String id : ids) {
            UserGroup userGroup = super.get(Long.parseLong(id));
            if (null != userGroup) {
                //判断用户组下面是否关联得有用户信息，如果有就不能删除
                List<UserGroupUser> userGroupUsers = findUserGroupUserByGroupIdDAO.getUserGroupUserByGroupId(userGroup.getId());
                if (null != userGroupUsers && 0 < userGroupUsers.size()) {
                    throw new BusinessException("角色["+userGroup.getName()+"]下面还有用户，不能被删除！");
                }
                //删除关联得资源
                delUserGroupResourceByGroupIdDAO.delUserGroupResourceByGroupId(userGroup.getId());
                super.remove(userGroup);
            }
        }
    }
}
