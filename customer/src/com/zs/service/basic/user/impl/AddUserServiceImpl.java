package com.zs.service.basic.user.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.dao.basic.usergroup.FindUserGroupByCreatorDAO;
import com.zs.dao.basic.usergroupuser.FindUserGroupUserByGroupIdDAO;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroup;
import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.user.AddUserService;
import com.zs.tools.HttpRequestTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 实现新增用户接口
 * Created by Allen on 2015/4/27.
 */
@Service("addUserService")
public class AddUserServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements AddUserService {

    @Resource
    private FindUserByZZDAO findUserByZZDAO;
    @Resource
    private FindUserGroupUserByGroupIdDAO findUserGroupUserByGroupIdDAO;
    @Resource
    private FindUserGroupByCreatorDAO findUserGroupByCreatorDAO;

    @Override
    @Transactional
    public void addUser(User user, long userGroupId, String zzCode, long loginId) throws Exception {
        if(null != user){
            UserGroup userGroup = findUserGroupByCreatorDAO.get(userGroupId);
            if(userGroup == null){
                throw new BusinessException("职务不存在！");
            }
            user.setLevel(userGroup.getLevel());

            //验证ZZ号是否已经存在
            User validUser = findUserByZZDAO.find(user.getZzCode());
            if(null != validUser && validUser.getZzCode().equals(user.getZzCode())){
                throw new BusinessException("ZZ已经存在");
            }
            //验证zz的真实性
            boolean isExistsZZ = HttpRequestTools.isExistsZZ(user.getZzCode());
            if(!isExistsZZ){
                //throw new BusinessException("ZZ不存在");
            }
            if(user.getLevel() == User.LEVEL_COMPANY){
                user.setParentId(0l);
                user.setParentSign(user.getZzCode());
            }else{
                if(user.getParentId() <= 0){
                    user.setParentId(loginId);
                }
                User parentUser = super.get(user.getParentId());
                if(parentUser.getLevel() == User.LEVEL_BUSINESS){
                    throw new BusinessException("业务员级别的用户下面不能在添加用户");
                }
                user.setParentSign(parentUser.getParentSign() + "-" + user.getZzCode());
            }
            user.setCreator(zzCode);
            user.setOperator(zzCode);
            super.save(user);

            //添加用户和角色的关联
            UserGroupUser userGroupUser = new UserGroupUser();
            userGroupUser.setUserId(user.getId());
            userGroupUser.setUserGroupId(userGroupId);
            userGroupUser.setCreator(zzCode);
            findUserGroupUserByGroupIdDAO.save(userGroupUser);
        }
    }
}
