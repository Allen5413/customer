package com.zs.service.basic.user;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;

/**
 * 删除
 * Created by Allen on 2016/3/4.
 */
public interface DelUserService extends EntityService<User> {

    /**
     * 新增用户信息
     * @param id

     * @throws Exception
     */
    public void del(String zzCode, String... ids)throws Exception;
}
