package com.zs.dao.customerlinkman;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerLankman;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/8.
 */
public interface DelLinkmanByIdDAO extends EntityJpaDao<CustomerLankman, Long> {

    @Modifying
    @Query("DELETE FROM CustomerLankman where id = ?1")
    public void del(long id);
}
