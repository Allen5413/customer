package com.zs.service.basic.usergroup;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroup;

/**
 * 删除用户组信息
 * Created by Allen on 2015/4/27.
 */
public interface DelUserGroupService extends EntityService<UserGroup> {

    public void delUserGroup(String... ids)throws Exception;
}
