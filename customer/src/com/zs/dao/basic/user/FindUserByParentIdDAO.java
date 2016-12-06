package com.zs.dao.basic.user;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/11/24.
 */
public interface FindUserByParentIdDAO extends EntityJpaDao<User, Long> {
    @Query("from User where parentId = ?1")
    public List<User> findForNotState(long userId);

    @Query("from User where parentId = ?1 and state = 1")
    public List<User> find(long userId);
}
