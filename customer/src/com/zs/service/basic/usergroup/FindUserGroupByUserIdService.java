package com.zs.service.basic.usergroup;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroup;

import java.util.List;

/**
 * Created by Allen on 2015/5/15.
 */
public interface FindUserGroupByUserIdService extends EntityService<UserGroup> {
    public List<UserGroup> find(long userId);
}
