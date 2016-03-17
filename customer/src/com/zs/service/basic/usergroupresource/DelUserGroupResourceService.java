package com.zs.service.basic.usergroupresource;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroupResource;

/**
 * 删除用户组关联资源信息
 * Created by Allen on 2015/4/27.
 */
public interface DelUserGroupResourceService extends EntityService<UserGroupResource> {

    public void delUserGroupResource(Long id)throws Exception;
}
