package com.zs.dao.basic.user;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 查询所有公司级别的用户
 * Created by Allen on 2016/11/24.
 */
public interface FindUserForCompanyDAO extends EntityJpaDao<User, Long> {
    @Query("from User where state = 1 and level = 0 order by name")
    public List<User> find();
}
