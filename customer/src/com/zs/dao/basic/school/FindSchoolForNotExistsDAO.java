package com.zs.dao.basic.school;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.School;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindSchoolForNotExistsDAO extends EntityJpaDao<School,Long> {
    @Query("select s from School s where not EXISTS (select c from Customer c where c.no = s.code) order by s.name")
    public List<School> find();
}
