package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByLevelDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupByLevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/13.
 */
@Service("findUserGroupByLevelService")
public class FindUserGroupByLevelServiceImpl extends EntityServiceImpl<UserGroup, FindUserGroupByLevelDAO>
        implements FindUserGroupByLevelService {

    @Resource
    private FindUserGroupByLevelDAO findUserGroupByLevelDAO;

    @Override
    public List<UserGroup> find(int level)throws Exception{
        return findUserGroupByLevelDAO.find(level);
    }
}
