package com.zs.service.basic.menu.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.menu.MenuDAO;
import com.zs.domain.basic.Menu;
import com.zs.service.basic.menu.FindMenuService;
import org.springframework.stereotype.Service;

/**
 * 实现查询菜单信息接口
 * Created by Allen on 2015/4/27.
 */
@Service("findMenuService")
public class FindMenuServiceImpl extends EntityServiceImpl<Menu, MenuDAO> implements FindMenuService {
}
