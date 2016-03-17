package com.zs.service.basic.user;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;

import java.util.Map;

/**
 * 分页查询用户信息
 * Created by Allen on 2015/4/27.
 */
public interface FindUserPageByWhereService extends EntityService<User> {

    public PageInfo<User> findPageByWhere(PageInfo pageInfo, Map<String, String> map)throws Exception;
}
