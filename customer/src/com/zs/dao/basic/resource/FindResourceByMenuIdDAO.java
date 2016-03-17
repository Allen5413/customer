package com.zs.dao.basic.resource;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.Resource;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 通过菜单ID查询资源信息
 * Created by Allen on 2015/4/28.
 */
public interface FindResourceByMenuIdDAO extends EntityJpaDao<Resource,Long> {
    @Query("from Resource where menuId = ?1")
    public List<Resource> getResourceByMenuID(Long menuId);
}
