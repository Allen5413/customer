package com.zs.service.basic.usergroupresource.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroupresource.UserGroupResourceDAO;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.usergroupresource.DelUserGroupResourceService;
import org.springframework.stereotype.Service;

/**
 * 实现删除用户组关联资源接口
 * Created by Allen on 2015/4/27.
 */
@Service("delUserGroupResourceService")
public class DelUserGroupResourceServiceImpl extends EntityServiceImpl<UserGroupResource, UserGroupResourceDAO> implements DelUserGroupResourceService {

    @Override
    public void delUserGroupResource(Long id) throws Exception {
        UserGroupResource userGroupResource = super.get(id);
        if(null != userGroupResource){
            super.remove(userGroupResource);
        }
    }
}
