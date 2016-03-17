package com.zs.dao.basic.usergroupuser;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroupUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2015/5/21.
 */
public interface DelUserGroupUserByLoginNameAndTypeDAO extends EntityJpaDao<UserGroupUser, Long> {
    @Modifying
    @Query("delete from UserGroupUser where userName = ?1 and type = ?2")
    public void delUserGroupUserByLoginNameAndType(String loginName, int type);
}
