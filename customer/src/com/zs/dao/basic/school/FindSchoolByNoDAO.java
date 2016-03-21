package com.zs.dao.basic.school;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.School;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by Allen on 2016/3/21.
 */
public interface FindSchoolByNoDAO extends EntityJpaDao<School, Long> {
    @Query("FROM School WHERE code = ?")
    public School find(String code);
}
