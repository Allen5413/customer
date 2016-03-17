package com.zs.dao.interview;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Interview;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/9.
 */
public interface DelInterviewByCustomerIdDAO extends EntityJpaDao<Interview, Long> {
    @Modifying
    @Query("DELETE FROM Interview WHERE customerId = ?1")
    public void del(long customerId)throws Exception;
}
