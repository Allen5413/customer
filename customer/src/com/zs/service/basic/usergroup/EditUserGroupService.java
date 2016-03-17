package com.zs.service.basic.usergroup;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroup;

/**
 * 编辑用户组信息
 * Created by Allen on 2015/4/27.
 */
public interface EditUserGroupService extends EntityService<UserGroup> {

    public void editUserGroup(UserGroup userGroup, String resourceIds, String loginName)throws Exception;
}
