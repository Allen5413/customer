package com.zs.dao.basic.user;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/12/2.
 */
public interface FindUserByParenSignDAO extends EntityJpaDao<User, Long> {
    @Query("from User where parentSign like ?1")
    public List<User> find(String zzCode);

    @Query("from User where parentSign like ?1 and level = ?2")
    public List<User> findAndLevel(String zzCode, int level);
}
