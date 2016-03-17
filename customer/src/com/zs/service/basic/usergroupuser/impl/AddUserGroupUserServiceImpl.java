package com.zs.service.basic.usergroupuser.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroupuser.DelUserGroupUserByLoginNameAndTypeDAO;
import com.zs.dao.basic.usergroupuser.UserGroupUserDAO;
import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.usergroupuser.AddUserGroupUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2015/5/21.
 */
@Service("addUserGroupUserService")
public class AddUserGroupUserServiceImpl extends EntityServiceImpl<UserGroupUser, UserGroupUserDAO>
        implements AddUserGroupUserService {

    @Resource
    private DelUserGroupUserByLoginNameAndTypeDAO delUserGroupUserByLoginNameAndTypeDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addUserGroupUser(String userLoginNames, String userGroupIds, int type, String loginName) throws Exception {
        if(StringUtils.isEmpty(userLoginNames)){
            throw new BusinessException("没有传入用户登录名");
        }
//        String[] userLoginNameArray =  userLoginNames.split(",");
//        for(String userLoginName : userLoginNameArray) {
//            //删除掉以前的关联
//            delUserGroupUserByLoginNameAndTypeDAO.delUserGroupUserByLoginNameAndType(userLoginName, type);
//            if (!StringUtils.isEmpty(userGroupIds)) {
//                //循环增加新的关联
//                String[] userGroupIdArray = userGroupIds.split(",");
//                if (null != userGroupIdArray && 0 < userGroupIdArray.length) {
//                    for (String userGroupId : userGroupIdArray) {
//                        UserGroupUser userGroupUser = new UserGroupUser();
//                        userGroupUser.setUserId(userLoginName);
//                        userGroupUser.setUserGroupId(Long.parseLong(userGroupId));
//                        userGroupUser.setType(type);
//                        userGroupUser.setCreator(loginName);
//                        super.save(userGroupUser);
//                    }
//                }
//            }
//        }
    }
}
