package com.zs.service.basic.user;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;

/**
 * 验证用户登录
 * Created by Allen on 2015/4/27.
 */
public interface ValidateLoginService extends EntityService<User> {
    /**
     * 验证用户登录
     * @param zzCode
     * @param pwd
     * @return
     */
    public User validateLogin(String zzCode, String pwd)throws Exception;
}
