package com.zs.dao.customerstate;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerState;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2017/1/9.
 */
public interface FindCustomerStateTotalCountDAO extends EntityJpaDao<CustomerState, Long> {
    @Query(nativeQuery = true, value = "select cs.name, count(*) count from customer c, customer_state cs " +
            "where c.customer_state_id = cs.id group by name")
    public List<Object[]> find()throws Exception;
}
