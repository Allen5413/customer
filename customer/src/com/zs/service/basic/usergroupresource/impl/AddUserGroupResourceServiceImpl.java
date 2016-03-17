package com.zs.service.basic.usergroupresource.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroupresource.DelUserGroupResourceByGroupIdDAO;
import com.zs.dao.basic.usergroupresource.UserGroupResourceDAO;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.usergroupresource.AddUserGroupResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 实现新增用户组关联资源接口
 * Created by Allen on 2015/4/27.
 */
@Service("addUserGroupResourceService")
public class AddUserGroupResourceServiceImpl extends EntityServiceImpl<UserGroupResource, UserGroupResourceDAO> implements AddUserGroupResourceService {

    @Resource
    private DelUserGroupResourceByGroupIdDAO delUserGroupResourceByGroupIdDAO;

    @Override
    @Transactional
    public void add(long groupId, String isBrowse, String isAdmin, String isAssign, String zzCode, String... rIds) throws Exception {
        //删掉该角色之前的资源关联
        delUserGroupResourceByGroupIdDAO.delUserGroupResourceByGroupId(groupId);
        //重新添加新的关联资源

        //客户资料资源特殊处理
        if(!StringUtils.isEmpty(isBrowse)){
            UserGroupResource userGroupResource = new UserGroupResource();
            userGroupResource.setResourceId(1l);
            userGroupResource.setUserGroupId(groupId);
            userGroupResource.setIsBrowse(StringUtils.isEmpty(isBrowse) ? null : Integer.parseInt(isBrowse));
            userGroupResource.setIsAdmin(StringUtils.isEmpty(isAdmin) ? null : Integer.parseInt(isAdmin));
            userGroupResource.setIsAssign(Integer.parseInt(isAssign));
            userGroupResource.setCreator(zzCode);
            super.save(userGroupResource);
        }

        //其他资源
        if(null != rIds){
            for(String rId : rIds){
                UserGroupResource userGroupResource = new UserGroupResource();
                userGroupResource.setResourceId(Long.parseLong(rId));
                userGroupResource.setUserGroupId(groupId);
                userGroupResource.setCreator(zzCode);
                super.save(userGroupResource);
            }
        }
    }
}
