package com.zs.dao.basic.resource;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.Resource;
import org.springframework.data.jpa.repository.Query;

/**
 * 通过名称和菜单ID查询资源信息
 * Created by Allen on 2015/4/28.
 */
public interface FindResourceByNameAndMenuIdDAO extends EntityJpaDao<Resource,Long> {
    @Query("from Resource where name = ?1 and  menuId = ?2")
    public Resource getResourceByNameAndMenuID(String name, Long menuId);
}
