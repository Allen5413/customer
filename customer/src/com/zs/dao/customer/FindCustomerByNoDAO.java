package com.zs.dao.customer;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Customer;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerByNoDAO extends EntityJpaDao<Customer, Long> {
    @Query("FROM Customer where no = ?1")
    public Customer find(String no);
}
