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

        long groupId = Long.parseLong(paramsMap.get("groupId"));

        String sql = "SELECT r.id, r.name, ugr.user_group_id, ugr.is_browse, ugr.is_admin, ugr.is_assign " +
                "FROM resource r LEFT JOIN user_group_resource ugr on r.id = ugr.resource_id and ugr.user_group_id = ? " +
                "order by r.menu_id, r.id";
        Object[] param = {groupId};
        List<Object[]> list = super.sqlQueryByNativeSql(sql, param);
        return list;
    }
}
