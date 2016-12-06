package com.zs.dao.basic.usergroup;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/11/23.
 */
public interface FindUserGroupForParentSignByZzCodeDAO extends EntityJpaDao<UserGroup, Long> {
    @Query("select ug from UserGroup ug, UserGroupUser ugu, User u " +
            "where ug.id = ugu.userGroupId and ugu.userId = u.id and u.parentSign like CONCAT('%',?1,'%')")
    public List<UserGroup> find(String zzCode);
}
