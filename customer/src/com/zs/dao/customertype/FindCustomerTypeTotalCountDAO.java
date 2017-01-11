package com.zs.dao.customertype;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerState;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2017/1/9.
 */
public interface FindCustomerTypeTotalCountDAO extends EntityJpaDao<CustomerState, Long> {
    @Query(nativeQuery = true, value = "select ct.name, count(*) count from customer c, customer_type ct " +
            "where c.customer_type_id = ct.id group by name")
    public List<Object[]> find()throws Exception;
}
