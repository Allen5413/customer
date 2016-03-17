package com.zs.service.basic.usergroup.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.UserGroupDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupService;
import org.springframework.stereotype.Service;

/**
 * 实现查询用户组信息接口
 * Created by Allen on 2015/4/27.
 */
@Service("findUserGroupService")
public class FindUserGroupServiceImpl extends EntityServiceImpl<UserGroup, UserGroupDAO> implements FindUserGroupService {
}
