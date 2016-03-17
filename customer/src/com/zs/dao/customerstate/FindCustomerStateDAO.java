package com.zs.dao.customerstate;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerState;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerStateDAO extends EntityJpaDao<CustomerState, Long> {
    @Query("FROM CustomerState ORDER BY createTime")
    public List<CustomerState> findAll();
}
