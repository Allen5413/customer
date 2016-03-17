package com.zs.dao.basic.area;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.Area;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindAreaForProvinceDAO extends EntityJpaDao<Area, Long> {

    @Query("FROM Area where parentCode = 0 ORDER BY code")
    public List<Area> find();
}
