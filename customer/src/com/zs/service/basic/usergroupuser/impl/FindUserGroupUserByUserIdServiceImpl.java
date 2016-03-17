package com.zs.service.basic.usergroupuser.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroupuser.FindUserGroupUserByUserIdDAO;
import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.usergroupuser.FindUserGroupUserByUserIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/10.
 */
@Service("findUserGroupUserByUserIdService")
public class FindUserGroupUserByUserIdServiceImpl extends EntityServiceImpl<UserGroupUser, FindUserGroupUserByUserIdDAO> implements FindUserGroupUserByUserIdService {

    @Resource
    private FindUserGroupUserByUserIdDAO findUserGroupUserByUserIdDAO;

    @Override
    public UserGroupUser find(long userId) {
        return findUserGroupUserByUserIdDAO.find(userId);
    }
}
