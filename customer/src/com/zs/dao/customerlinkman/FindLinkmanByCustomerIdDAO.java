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

    @Query(nativeQuery = true, value = "select cl.id, cl.`name`, cl.phone, cl.post, cl.remark, count(i.id) " +
            "from customer_lankman cl LEFT JOIN interview i on cl.id = i.customer_lankman_id " +
            "where cl.customer_id = ?1 and cl.state = 0 " +
            "group by cl.id, cl.`name`, cl.phone, cl.post, cl.remark " +
            "order by cl.create_time")
    public List<Object[]> findForInterviewCount(long customerId);
}
