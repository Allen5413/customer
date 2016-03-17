package com.zs.dao.basic.area;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.Area;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 2016/3/8.
 */
public interface FindAreaByCodeDAO extends EntityJpaDao<Area, Long> {

    @Query("FROM Area where code = ?1")
    public Area find(String code);
}
