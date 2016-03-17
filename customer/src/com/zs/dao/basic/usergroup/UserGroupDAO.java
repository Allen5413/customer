package com.zs.dao.basic.usergroup;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroup;

/**
 * 用于不需要查询的service用
 * Created by Allen on 2015/4/27.
 */
public interface UserGroupDAO extends EntityJpaDao<UserGroup,Long> {
}
