package com.zs.dao.basic.usergroupresource.impl;

import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindListByWhereDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 查询一个角色关联了哪些资源
 * Created by Allen on 2016/3/10.
 */
@Service("findResourceByGroupIdDAO")
public class FindUserGroupResourceByGroupIdDAOImpl extends BaseQueryDao
        implements FindListByWhereDAO {
    @Override
    public List<Object[]> findListByWhere(Map<String, String> paramsMap, Map<String, Boolean> sortMap) {

        long userId = Long.parseLong(paramsMap.get("userId"));
        long groupId = Long.parseLong(paramsMap.get("groupId"));

        String sql = "SELECT t.id, t.name, ugr2.user_group_id, ugr2.is_browse, ugr2.is_admin, ugr2.is_assign " +
                "FROM (select r.* from resource r, user_group_user ugu, user_group_resource ugr where ugu.user_group_id = ugr.user_group_id and ugr.resource_id = r.id and ugu.user_id = ?) t " +
                "LEFT JOIN user_group_resource ugr2 on t.id = ugr2.resource_id and ugr2.user_group_id = ? " +
                "order by t.menu_id, t.id";
        Object[] param = {userId, groupId};
        List<Object[]> list = super.sqlQueryByNativeSql(sql, param);
        return list;
    }
}
