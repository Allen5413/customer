package com.zs.dao.basic.usergroupuser;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroupUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2015/5/18.
 */
public interface FindUserGroupUserByGroupIdDAO extends EntityJpaDao<UserGroupUser, Long> {
    @Query("from UserGroupUser where userGroupId = ?1")
    public List<UserGroupUser> getUserGroupUserByGroupId(Long userGroupId);
}
