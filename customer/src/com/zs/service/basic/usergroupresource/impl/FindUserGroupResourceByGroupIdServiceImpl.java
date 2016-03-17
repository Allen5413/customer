package com.zs.service.basic.usergroupresource.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindListByWhereDAO;
import com.zs.dao.basic.usergroupresource.UserGroupResourceDAO;
import com.zs.domain.basic.UserGroupResource;
import com.zs.service.basic.usergroupresource.FindUserGroupResourceByGroupIdService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/10.
 */
@Service("findUserGroupResourceByGroupIdService")
public class FindUserGroupResourceByGroupIdServiceImpl extends EntityServiceImpl<UserGroupResource, UserGroupResourceDAO> implements FindUserGroupResourceByGroupIdService {

    @Resource
    private FindListByWhereDAO findResourceByGroupIdDAO;

    @Override
    @Transactional
    public List<JSONObject> find(String groupId) {
        List<JSONObject> retureList = new ArrayList<JSONObject>();
        Map<String, String> parmaMap = new HashMap<String, String>(1);
        parmaMap.put("groupId", groupId);
        List<Object[]> resultList = findResourceByGroupIdDAO.findListByWhere(parmaMap, null);
        if(null != resultList){
            for(Object[] obj : resultList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rId", obj[0]);
                jsonObject.put("name", obj[1]);
                jsonObject.put("gId", obj[2]);
                jsonObject.put("isBrowse", obj[3]);
                jsonObject.put("isAdmin", obj[4]);
                jsonObject.put("isAssign", obj[5]);
                retureList.add(jsonObject);
            }
        }
        return retureList;
    }
}
