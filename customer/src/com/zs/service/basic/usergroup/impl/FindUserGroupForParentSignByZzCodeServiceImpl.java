package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByCreatorDAO;
import com.zs.dao.basic.usergroup.FindUserGroupForParentSignByZzCodeDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupForParentSignByZzCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/11/23.
 */
@Service("findUserGroupForParentSignByZzCodeService")
public class FindUserGroupForParentSignByZzCodeServiceImpl extends EntityServiceImpl<UserGroup, FindUserGroupByCreatorDAO>
    implements FindUserGroupForParentSignByZzCodeService{

    @Resource
    private FindUserGroupForParentSignByZzCodeDAO findUserGroupForParentSignByZzCodeDAO;

    @Override
    public List<UserGroup> find(String zzCode) {
        return findUserGroupForParentSignByZzCodeDAO.find(zzCode);
    }
}
