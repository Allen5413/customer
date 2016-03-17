package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByUserIdDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupByUserIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2015/5/15.
 */
@Service("findUserGroupByUserIdService")
public class FindUserGroupByUserIdServiceImpl extends EntityServiceImpl<UserGroup, FindUserGroupByUserIdDAO>
    implements FindUserGroupByUserIdService {

    @Resource
    private FindUserGroupByUserIdDAO findUserGroupByUserIdDAO;

    @Override
    public List<UserGroup> find(long userId) {
        return findUserGroupByUserIdDAO.find(userId);
    }
}
