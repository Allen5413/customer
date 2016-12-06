package com.zs.dao.interview;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Interview;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/9.
 */
public interface FindInterviewByLinkManIdDAO extends EntityJpaDao<Interview, Long> {
    @Query("from Interview where customerLankmanId = ?1")
    public List<Interview> find(long customerLankmanId);
}
