package com.zs.dao.customer;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Customer;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/25.
 */
public interface FindCustomerByNameDAO extends EntityJpaDao<Customer, Long> {
    @Query("FROM Customer where name = ?1")
    public Customer find(String name);
}
