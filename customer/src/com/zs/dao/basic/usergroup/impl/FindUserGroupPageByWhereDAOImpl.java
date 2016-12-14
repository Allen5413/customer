package com.zs.dao.basic.usergroup.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.domain.basic.UserGroup;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 实现分页查询用户组信息接口
 * Created by Allen on 2015/4/28.
 */
@Service("findUserGroupPageByWhereDAO")
public class FindUserGroupPageByWhereDAOImpl extends BaseQueryDao implements FindPageByWhereDAO {
    @Override
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> paramsMap, Map<String, Boolean> sortMap) {
        PageInfo<UserGroup> userGroupPageInfo = new PageInfo<UserGroup>();
        userGroupPageInfo.setCurrentPage(pageInfo.getCurrentPage());
        userGroupPageInfo.setCountOfCurrentPage(pageInfo.getCountOfCurrentPage());

        StringBuilder sql = new StringBuilder("FROM UserGroup where 1=1 ");
        String name = paramsMap.get("name");
        String level = paramsMap.get("level");
        String creator = paramsMap.get("creator");

        List<Object> param = new ArrayList<Object>();
        if(!StringUtils.isEmpty(name)){
            sql.append(" and name like ? ");
            param.add("%"+name+"%");
        }
        if(!StringUtils.isEmpty(level)){
            if(Integer.parseInt(level) == UserGroup.LEVEL_COMPANY) {
                sql.append(" and level >= ? ");
            }else{
                sql.append(" and level > ? ");
            }
            param.add(Integer.parseInt(level));
        }
        if(!StringUtils.isEmpty(creator)){
            sql.append(" and creator = ? ");
            param.add(creator);
        }
        sql.append(" order by ");
        for (Iterator it = sortMap.keySet().iterator(); it.hasNext();){
            String key = it.next().toString();
            sql.append(key);
            sql.append(" ");
            sql.append(sortMap.get(key) ? "asc" : "desc");
        }
        userGroupPageInfo = super.pagedQueryByJpql(userGroupPageInfo, sql.toString(), param.toArray());
        return userGroupPageInfo;
    }
}
