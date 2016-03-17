package com.zs.dao.basic.usergroupuser;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroupUser;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/10.
 */
public interface FindUserGroupUserByUserIdDAO extends EntityJpaDao<UserGroupUser, Long> {
    @Query("from UserGroupUser where userId = ?1")
    public UserGroupUser find(Long userId);
}
