package com.zs.dao.basic.user;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.User;
import org.springframework.data.jpa.repository.Query;

/**
 * 通过zzCode查询用户信息
 * Created by Allen on 2015/4/27.
 */
public interface FindUserByZZDAO extends EntityJpaDao<User,Long> {

    @Query("from User where zzCode = ?1")
    public User find(String zzCode);
}
