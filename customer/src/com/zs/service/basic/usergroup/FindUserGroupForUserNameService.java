package com.zs.service.basic.usergroup;

import com.alibaba.fastjson.JSONObject;
import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroup;

import java.util.List;

/**
 * Created by Allen on 2016/11/23.
 */
public interface FindUserGroupForUserNameService extends EntityService<UserGroup> {
    public List<JSONObject> find()throws Exception;
}
