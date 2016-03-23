package com.zs.dao.customerlinkman;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerLankman;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindLinkmanByCustomerIdDAO extends EntityJpaDao<CustomerLankman, Long> {

    @Query("FROM CustomerLankman WHERE customerId = ?1 AND state = 0 ORDER BY createTime")
    public List<CustomerLankman> find(long customerId);
}
