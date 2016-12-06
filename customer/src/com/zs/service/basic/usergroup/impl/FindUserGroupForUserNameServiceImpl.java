package com.zs.service.basic.usergroup.impl;

import com.alibaba.fastjson.JSONObject;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.usergroup.FindUserGroupByCreatorDAO;
import com.zs.dao.basic.usergroup.FindUserGroupForUserNameDAO;
import com.zs.domain.basic.UserGroup;
import com.zs.service.basic.usergroup.FindUserGroupForUserNameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2016/11/23.
 */
@Service("findUserGroupForUserNameService")
public class FindUserGroupForUserNameServiceImpl extends EntityServiceImpl<UserGroup, FindUserGroupByCreatorDAO>
    implements FindUserGroupForUserNameService{

    @Resource
    private FindUserGroupForUserNameDAO findUserGroupForUserNameDAO;

    @Override
    public List<JSONObject> find()throws Exception {
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        List<Object[]> list = findUserGroupForUserNameDAO.find();
        if(null != list && 0 < list.size()){
            for (Object[] objs : list){
                JSONObject json = new JSONObject();
                json.put("id", objs[0]);
                json.put("name", objs[1]);
                json.put("remark", null == objs[2] ? "" : objs[2]);
                json.put("creator", objs[3]);
                json.put("userName", objs[4]);
                resultList.add(json);
            }
        }
        return resultList;
    }
}
