package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByCreatorDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupByCreatorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/11/23.
 */
@Service("findUserGroupByCreatorService")
public class FindUserGroupByCreatorServiceImpl extends EntityServiceImpl<UserGroup, FindUserGroupByCreatorDAO>
    implements FindUserGroupByCreatorService{

    @Resource
    private FindUserGroupByCreatorDAO findUserGroupByCreatorDAO;

    @Override
    public List<UserGroup> find(String zzCode)throws Exception{
        return findUserGroupByCreatorDAO.find(zzCode);
    }
}
