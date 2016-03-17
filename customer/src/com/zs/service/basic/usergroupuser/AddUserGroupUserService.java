package com.zs.service.basic.usergroupuser;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroupUser;

/**
 * Created by Allen on 2015/5/21.
 */
public interface AddUserGroupUserService extends EntityService<UserGroupUser> {

    public void addUserGroupUser(String userLoginName, String userGroupIds, int type, String loginName)throws Exception;
}
