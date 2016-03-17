package com.zs.dao.basic.usergroupresource;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroupResource;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2015/5/18.
 */
public interface FindUserGroupResourceByGroupIdDAO extends EntityJpaDao<UserGroupResource, Long> {
    @Query("from UserGroupResource where userGroupId = ?1")
    public List<UserGroupResource> getUserGroupResourceByGroupId(Long userGroupId);
}
