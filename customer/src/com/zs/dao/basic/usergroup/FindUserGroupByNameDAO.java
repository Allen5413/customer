package com.zs.dao.basic.usergroup;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroup;
import org.springframework.data.jpa.repository.Query;

/**
 * 通过名称查询用户组信息
 * Created by Allen on 2015/4/28.
 */
public interface FindUserGroupByNameDAO extends EntityJpaDao<UserGroup,Long> {
    @Query("from UserGroup where name = ?1")
    public UserGroup getUserGroupByName(String name);
}
