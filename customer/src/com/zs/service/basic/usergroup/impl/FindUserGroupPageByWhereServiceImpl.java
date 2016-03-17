package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.dao.basic.usergroup.UserGroupDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupPageByWhereService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 实现分页查询用户组信息接口
 * Created by Allen on 2015/4/28.
 */
@Service("findUserGroupPageByWhereService")
public class FindUserGroupPageByWhereServiceImpl extends EntityServiceImpl<UserGroup, UserGroupDAO>
        implements FindUserGroupPageByWhereService  {

    @Resource
    public FindPageByWhereDAO findUserGroupPageByWhereDAO;

    @Override
    public PageInfo<UserGroup> findPageByWhere(PageInfo<UserGroup> pageInfo, Map<String, String> map, Map<String, Boolean> sortMap) throws Exception {
        return findUserGroupPageByWhereDAO.findPageByWhere(pageInfo, map, sortMap);
    }
}
