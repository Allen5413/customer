package com.zs.service.basic.user;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;

/**
 * 新增用户信息
 * Created by Allen on 2015/4/27.
 */
public interface AddUserService extends EntityService<User> {

    /**
     * 新增用户信息
     * @param user
     * @param zzCode
     * @throws Exception
     */
    public void addUser(User user, long userGroupId, String zzCode, long loginId)throws Exception;
}
