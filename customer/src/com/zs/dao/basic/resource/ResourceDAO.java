package com.zs.dao.basic.resource;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.Resource;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用于不需要查询的service用
 * Created by Allen on 2015/4/28.
 */
public interface ResourceDAO extends EntityJpaDao<Resource,Long> {
    @Query("from Resource order by menuId, id")
    public List<Resource> findAll();
}
