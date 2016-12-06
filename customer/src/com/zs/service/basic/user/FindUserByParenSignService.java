package com.zs.service.basic.user;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;

import java.util.List;

/**
 * Created by Allen on 2016/12/2.
 */
public interface FindUserByParenSignService extends EntityService<User> {
    public List<User> find(String zzCode);
}
