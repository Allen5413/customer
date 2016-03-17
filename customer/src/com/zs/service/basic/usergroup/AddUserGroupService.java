package com.zs.service.basic.usergroup;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroup;

/**
 * 新增用户组信息
 * Created by Allen on 2015/4/27.
 */
public interface AddUserGroupService extends EntityService<UserGroup> {

    public void addUserGroup(UserGroup userGroup, String zzCode)throws Exception;
}
