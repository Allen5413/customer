package com.zs.dao.customer;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 查询一个用户下关联的客户
 * Created by Allen on 2016/3/4.
 */
public interface FindCustomerByUserIdDAO extends EntityJpaDao<Customer, Long> {
    @Query("FROM Customer where userId = ?1")
    public List<Customer> find(long userId);
}
