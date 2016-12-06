package com.zs.service.basic.usergroupresource;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroupResource;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Allen on 2016/3/10.
 */
public interface FindUserGroupResourceByGroupIdService extends EntityService<UserGroupResource> {
    public List<JSONObject> find(String groupId, long userId);
}
