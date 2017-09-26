package com.zs.dao.basic.school;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.School;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindSchoolDAO extends EntityJpaDao<School, Long> {
    @Query("FROM School ORDER BY code")
    public List<School> findAll();
}
