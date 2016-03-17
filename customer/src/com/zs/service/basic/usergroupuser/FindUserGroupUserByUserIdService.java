package com.zs.service.basic.usergroupuser;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroupUser;

/**
 * Created by Allen on 2016/3/10.
 */
public interface FindUserGroupUserByUserIdService extends EntityService<UserGroupUser> {
    public UserGroupUser find(long userId);
}
