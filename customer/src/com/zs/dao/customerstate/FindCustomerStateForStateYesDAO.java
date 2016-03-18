package com.zs.dao.customerstate;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerState;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindCustomerStateForStateYesDAO extends EntityJpaDao<CustomerState, Long> {
    @Query("FROM CustomerState WHERE state = 0 ORDER BY id")
    public List<CustomerState> find();
}
