package com.zs.service.basic.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByParentIdDAO;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.dao.basic.user.FindUserForCompanyDAO;
import com.zs.domain.basic.User;
import com.zs.service.basic.user.FindUserForTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询用户上下级关系，返回easyui tree的json格式
 * Created by Allen on 2016/11/24.
 */
@Service("findUserForTreeService")
public class FindUserForTreeServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements FindUserForTreeService {

    @Resource
    private FindUserForCompanyDAO findUserForCompanyDAO;
    @Resource
    private FindUserByParentIdDAO findUserByParentIdDAO;

    @Override
    public JSONArray find(long userId, int isParent) throws Exception {
        long parentUserId = 0l;
        User user = super.get(userId);
        if(0 < userId){
            //查询该用户的上级用户
            if(1 == isParent) {
                User parentUser = super.get(user.getParentId());
                if (null != parentUser) {
                    parentUserId = parentUser.getId();
                }
            }else{
                parentUserId = user.getId();
            }
        }
        JSONArray jsonArray = new JSONArray();

        if(user.getLevel() == User.LEVEL_COMPANY) {
            //查询所有公司级别的用户
            List<User> companyUserList = findUserForCompanyDAO.find();
            for (User companyUser : companyUserList) {
                JSONObject json = new JSONObject();
                json.put("id", companyUser.getId());
                json.put("text", companyUser.getName());
                if (parentUserId == companyUser.getId()) {
                    json.put("checked", true);
                }
                this.dgFindUser(companyUser.getId(), json, parentUserId);
                jsonArray.add(json);
            }
        }else{
            JSONObject json = new JSONObject();
            json.put("id", user.getId());
            json.put("text", user.getName());
            if (parentUserId == user.getId()) {
                json.put("checked", true);
            }
            this.dgFindUser(user.getId(), json, parentUserId);
            jsonArray.add(json);
        }

        return jsonArray;
    }

    @Override
    public JSONArray findForEditUser(long userId, long loginId) throws Exception {
        long parentUserId = 0l;
        User user = super.get(userId);
        User loginUser = super.get(loginId);
        if(0 < userId){
            //查询该用户的上级用户
            User parentUser = super.get(user.getParentId());
            if (null != parentUser) {
                parentUserId = parentUser.getId();
            }
        }
        JSONArray jsonArray = new JSONArray();

        if(loginUser.getLevel() == User.LEVEL_COMPANY) {
            //查询所有公司级别的用户
            List<User> companyUserList = findUserForCompanyDAO.find();
            for (User companyUser : companyUserList) {
                JSONObject json = new JSONObject();
                json.put("id", companyUser.getId());
                json.put("text", companyUser.getName());
                if (parentUserId == companyUser.getId()) {
                    json.put("checked", true);
                }
                this.dgFindUser(companyUser.getId(), json, parentUserId);
                jsonArray.add(json);
            }
        }else{
            JSONObject json = new JSONObject();
            json.put("id", loginUser.getId());
            json.put("text", loginUser.getName());
            if (parentUserId == loginUser.getId()) {
                json.put("checked", true);
            }
            this.dgFindUser(loginUser.getId(), json, parentUserId);
            jsonArray.add(json);
        }

        return jsonArray;
    }

    private void dgFindUser(long userId, JSONObject json, long parentUserId){
        List<User> userList = findUserByParentIdDAO.find(userId);
        if(null != userList && 0 < userList.size()){
            JSONArray jsonArray = new JSONArray();
            for(User user : userList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", user.getId());
                jsonObject.put("text", user.getName());
                if(parentUserId == user.getId()){
                    jsonObject.put("checked", true);
                }
                jsonArray.add(jsonObject);
                json.put("children", jsonArray);

                this.dgFindUser(user.getId(), jsonObject, parentUserId);
            }
        }
    }
}
