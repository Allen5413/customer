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
    public List<Customer> find();

    @Query(nativeQuery = true, value = "select t.* from (" +
            "select c.* from user u, customer c where parent_sign like ?1 and u.id = c.user_id " +
            "union " +
            "select c.* from user u, customer c where parent_sign like ?1 and u.zz_code = c.creator" +
            ") t ORDER BY t.name")
    public List<Customer> findForChild(String zzCode);

    @Query("from Customer where creator = ?1 or userId = ?2 ORDER BY name")
    public List<Customer> findForMe(String zzCode, long userId);
}
