package com.zs.dao.customertype;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerTypeDAO extends EntityJpaDao<CustomerType, Long> {
    @Query("FROM CustomerType ORDER BY createTime")
    public List<CustomerType> findAll();
}
