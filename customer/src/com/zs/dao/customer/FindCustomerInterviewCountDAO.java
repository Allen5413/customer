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

    @Query(nativeQuery = true, value = "select c.id, c.name, count(*) count, max(i.operate_time) time " +
            "from (select c.* from user u, customer c where parent_sign like ?1 and u.id = c.user_id " +
            "union " +
            "select c.* from user u, customer c where parent_sign like ?1 and u.zz_code = c.creator " +
            ") c, " +
            "(SELECT i.* FROM interview i, user u WHERE i.creator = u.zz_code and u.parent_sign like ?1) i " +
            "where c.id = i.customer_id " +
            "group by c.id, c.name " +
            "ORDER BY time desc")
    public List<Object[]> findForChild(String zzCode);

    @Query(nativeQuery = true, value = "select c.id, c.name, count(*) count, max(i.operate_time) time " +
            "from (select c.* from customer c where c.creator = ?1 or c.user_id = ?2) c, " +
            "(SELECT i.* FROM interview i WHERE i.creator = ?1) i " +
            "where c.id = i.customer_id " +
            "group by c.id, c.name " +
            "ORDER BY time desc")
    public List<Object[]> findForMe(String zzCode, long userId);
}
