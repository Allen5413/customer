package com.zs.dao.basic.usergroup;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.UserGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 查询所有的用户组和创建人姓名
 * Created by Allen on 2015/5/15.
 */
public interface FindUserGroupForUserNameDAO extends EntityJpaDao<UserGroup, Long> {
    @Query(nativeQuery = true, value = "SELECT ug.id, ug.name, ug.remark, ug.creator, u.name userName FROM user_group ug, user u where ug.creator = u.zz_code order by ug.creator, ug.create_time")
    public List<Object[]> find()throws Exception;
}
