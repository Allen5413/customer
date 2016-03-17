package com.zs.service.basic.usergroupresource;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.UserGroupResource;

/**
 * 新增用户组关联资源信息
 * Created by Allen on 2015/4/27.
 */
public interface AddUserGroupResourceService extends EntityService<UserGroupResource> {

    public void add(long groupId, String isBrowse, String isAdmin, String isAssign, String zzCode, String... rIds)throws Exception;
}
