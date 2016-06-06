package com.zs.dao.customer;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 统计客户访谈记录数
 * Created by Allen on 2016/6/6.
 */
public interface FindCustomerInterviewCountDAO extends EntityJpaDao<Customer, Long>{
    @Query(nativeQuery = true, value = "select c.id, c.name, count(*) count, max(i.operate_time) time from customer c, interview i " +
            "where c.id = i.customer_id " +
            "group by c.id, c.name " +
            "ORDER BY time desc")
    public List<Object[]> find();
}
