package com.zs.service.basic.user.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.domain.basic.User;
import com.zs.service.basic.user.FindUserForStateEnableService;
import com.zs.service.basic.user.FindUserPageByWhereService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("findUserForStateEnableService")
public class FindUserForStateEnableServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements FindUserForStateEnableService {

    @Resource
    private FindUserPageByWhereService findUserPageByWhereService;

    @Override
    public List<JSONObject> find() throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(1);
        pageInfo.setCountOfCurrentPage(99999);

        Map<String, String> paramMap = new HashMap<String, String>(1);
        paramMap.put("state", User.STATE_ENABLE+"");
        findUserPageByWhereService.findPageByWhere(pageInfo, paramMap);
        List<JSONObject> userList = pageInfo.getPageResults();
        return userList;
    }
}
