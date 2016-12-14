package com.zs.service.basic.user.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByParentIdDAO;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.dao.basic.usergroup.FindUserGroupByCreatorDAO;
import com.zs.dao.basic.usergroupuser.FindUserGroupUserByUserIdDAO;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroup;
import com.zs.domain.basic.UserGroupUser;
import com.zs.service.basic.user.EditUserService;
import com.zs.tools.DateTools;
import com.zs.tools.HttpRequestTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实现修改用户接口
 * Created by Allen on 2015/4/27.
 */
@Service("editUserService")
public class EditUserServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements EditUserService {

    @Resource
    private FindUserByZZDAO findUserByZZDAO;
    @Resource
    private FindUserGroupUserByUserIdDAO findUserGroupUserByUserIdDAO;
    @Resource
    private FindUserByParentIdDAO findUserByParentIdDAO;
    @Resource
    private FindUserGroupByCreatorDAO findUserGroupByCreatorDAO;

    @Override
    @Transactional
    public void editUser(User user, long userGroupId, String zzCode, long loginId) throws Exception {
        if(null != user){
            UserGroup userGroup = findUserGroupByCreatorDAO.get(userGroupId);
            if(userGroup == null){
                throw new BusinessException("职务不存在！");
            }
            user.setLevel(userGroup.getLevel());
            //通过id查询User
            User oldUser = findUserByZZDAO.get(user.getId());
            //验证ZZ号是否已经存在
            User validUser = findUserByZZDAO.find(user.getZzCode());
            if(null != validUser && !validUser.getZzCode().equals(oldUser.getZzCode())){
                throw new BusinessException("ZZ已经存在");
            }
            //验证zz的真实性
            boolean isExistsZZ = HttpRequestTools.isExistsZZ(user.getZzCode());
            if(!isExistsZZ){
                //throw new BusinessException("ZZ不存在");
            }
            oldUser.setLevel(user.getLevel());
            if(oldUser.getLevel() == User.LEVEL_COMPANY){
                oldUser.setParentId(0l);
                oldUser.setParentSign(oldUser.getZzCode());
            }else{
                //不能修改自己的父级
                if(user.getId() != loginId) {
                    User parentUser = null;
                    if (user.getParentId() == 0) {
                        parentUser = super.get(loginId);
                    } else {
                        if(user.getParentId() == oldUser.getId()){
                            throw new BusinessException("不能把自己添加到自己下面");
                        }
                        parentUser = super.get(user.getParentId());
                    }
                    if(parentUser.getLevel() == User.LEVEL_BUSINESS){
                        throw new BusinessException("业务员级别的用户下面不能在添加用户");
                    }
                    oldUser.setParentId(parentUser.getId());
                    oldUser.setParentSign(parentUser.getParentSign() + "-" + user.getZzCode());
                    //查询被修改人的下级的parentsign标示，都要修改
                    this.dgEditUserForParentSign(oldUser.getId(), oldUser.getParentSign());
                }
            }
            oldUser.setZzCode(user.getZzCode());
            oldUser.setName(user.getName());
            oldUser.setPhone(user.getPhone());
            oldUser.setState(user.getState());
            oldUser.setRemark(user.getRemark());
            oldUser.setOperator(zzCode);
            oldUser.setOperateTime(DateTools.getLongNowTime());
            super.update(oldUser);

            //修改用户和角色的关联
            UserGroupUser userGroupUser = findUserGroupUserByUserIdDAO.find(user.getId());
            userGroupUser.setUserGroupId(userGroupId);
            userGroupUser.setCreator(zzCode);
            findUserGroupUserByUserIdDAO.update(userGroupUser);
        }
    }


    private void dgEditUserForParentSign(long parentUserId, String parentSign)throws Exception{
        List<User> userList = findUserByParentIdDAO.findForNotState(parentUserId);
        if(null != userList && 0 < userList.size()){
            for(User user : userList){
                user.setParentSign(parentSign+"-"+user.getZzCode());
                findUserByParentIdDAO.update(user);
                this.dgEditUserForParentSign(user.getId(), user.getParentSign());
            }
        }
    }
}
