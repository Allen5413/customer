package com.zs.dao.basic.usergroup;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 查询一个用户所创建的用户组
 * Created by Allen on 2015/5/15.
 */
public interface FindUserGroupByCreatorDAO extends EntityJpaDao<UserGroup, Long> {
    @Query("from UserGroup where creator = ?1 order by createTime")
    public List<UserGroup> find(String zzCode)throws Exception;
}
