package com.zs.service.basic.user;

import com.alibaba.fastjson.JSONArray;
import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.User;

/**
 * Created by Allen on 2016/11/24.
 */
public interface FindUserForTreeService extends EntityService<User> {
    public JSONArray find(long userId, int isParent)throws Exception;

    /**
     * 针对于登录用户不是公司级别的用户，修改自己下级的用户时用
     * @param userId
     * @param loginId
     * @return
     * @throws Exception
     */
    public JSONArray findForEditUser(long userId, long loginId)throws Exception;
}
