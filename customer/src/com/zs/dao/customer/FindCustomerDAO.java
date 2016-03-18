package com.zs.dao.customer;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindCustomerDAO extends EntityJpaDao<Customer, Long> {
    @Query("FROM Customer ORDER BY name")
    public List<Customer> findForOrderByName();
}
