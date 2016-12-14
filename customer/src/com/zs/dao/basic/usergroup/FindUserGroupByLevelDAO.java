package com.zs.dao.basic.usergroup;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 查询一个级别的用户组
 * Created by Allen on 2015/5/15.
 */
public interface FindUserGroupByLevelDAO extends EntityJpaDao<UserGroup, Long> {
    @Query("from UserGroup where level > ?1 order by createTime")
    public List<UserGroup> find(int level)throws Exception;
}
