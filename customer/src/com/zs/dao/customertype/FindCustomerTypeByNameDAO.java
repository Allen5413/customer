package com.zs.dao.customertype;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerType;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/9.
 */
public interface FindCustomerTypeByNameDAO extends EntityJpaDao<CustomerType, Long> {
    @Query("FROM CustomerType WHERE name = ?1")
    public CustomerType find(String name);
}
