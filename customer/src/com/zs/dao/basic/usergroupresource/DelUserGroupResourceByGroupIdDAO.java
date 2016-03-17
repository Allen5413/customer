package com.zs.dao.basic.usergroupresource;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroupResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2015/5/18.
 */
public interface DelUserGroupResourceByGroupIdDAO extends EntityJpaDao<UserGroupResource, Long> {
    @Modifying
    @Query("delete from UserGroupResource where userGroupId = ?1")
    public void delUserGroupResourceByGroupId(Long userGroupId);
}
