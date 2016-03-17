package com.zs.service.basic.user;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 查询状态为有效的用户
 * Created by Allen on 2016/3/8.
 */
public interface FindUserForStateEnableService extends EntityService<User> {
    public List<JSONObject> find()throws Exception;
}
