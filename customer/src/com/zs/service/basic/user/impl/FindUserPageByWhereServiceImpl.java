package com.zs.service.basic.user.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.domain.basic.User;
import com.zs.service.basic.user.FindUserPageByWhereService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("findUserPageByWhereService")
public class FindUserPageByWhereServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements FindUserPageByWhereService {

    @Resource
    private FindPageByWhereDAO findUserPageByWhereDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public PageInfo<User> findPageByWhere(PageInfo pageInfo, Map<String, String> map) throws Exception {
        pageInfo = findUserPageByWhereDAO.findPageByWhere(pageInfo, map, null);
        if(null != pageInfo && null != pageInfo.getPageResults() && 0 < pageInfo.getPageResults().size()){
            List<Object[]> list = pageInfo.getPageResults();
            JSONArray jsonArray = new JSONArray();
            for(Object[] objs : list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", objs[0]);
                jsonObject.put("zzCode", objs[1]);
                jsonObject.put("name", objs[2]);
                jsonObject.put("phone", objs[3]);
                jsonObject.put("ugName", objs[4]);
                jsonObject.put("state", Integer.parseInt(objs[5].toString()) == User.STATE_DISABLE ? "停用" : "启用");
                jsonObject.put("remark", objs[6]);
                jsonArray.add(jsonObject);
            }
            pageInfo.setPageResults(jsonArray);
        }
        return pageInfo;
    }
}
