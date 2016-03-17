package com.zs.service.basic.usergroup;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroup;

import java.util.Map;

/**
 * 分页查询用户组信息
 * Created by Allen on 2015/4/28.
 */
public interface FindUserGroupPageByWhereService extends EntityService<UserGroup> {
    public PageInfo<UserGroup> findPageByWhere(PageInfo<UserGroup> pageInfo, Map<String, String> map, Map<String, Boolean> sortMap)throws Exception;
}
