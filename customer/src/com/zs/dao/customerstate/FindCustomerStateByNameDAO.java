package com.zs.dao.customerstate;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerState;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/9.
 */
public interface FindCustomerStateByNameDAO extends EntityJpaDao<CustomerState, Long> {
    @Query("FROM CustomerState WHERE name = ?1")
    public CustomerState find(String name);
}
