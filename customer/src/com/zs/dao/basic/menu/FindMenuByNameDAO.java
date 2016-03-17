package com.zs.dao.basic.menu;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.basic.Menu;
import org.springframework.data.jpa.repository.Query;

/**
 * 通过名称查询菜单信息
 * Created by Allen on 2015/4/28.
 */
public interface FindMenuByNameDAO extends EntityJpaDao<Menu,Long> {
    @Query("from Menu where name = ?1")
    public Menu getMenuByName(String name);
}
