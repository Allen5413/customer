package com.zs.dao.basic.user.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.domain.basic.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现分页查询用户信息接口
 * Created by Allen on 2015/4/27.
 */
@Service("findUserPageByWhereDAO")
public class FindUserPageByWhereDAOImpl extends BaseQueryDao
        implements FindPageByWhereDAO {
    @Override
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> paramsMap, Map<String, Boolean> sortMap) {

        List<Object> param = new ArrayList<Object>();
        String groupId = paramsMap.get("groupId");
        String zzCode = paramsMap.get("zzCode");
        String state = paramsMap.get("state");
        String name = paramsMap.get("name");
        String level = paramsMap.get("level");
        String loginUserLevel = paramsMap.get("loginUserLevel");
        String loginUserZzCode = paramsMap.get("loginUserZzCode");

        String field = "u.id, u.zz_code, u.name, u.phone, ug.name ugName, u.state, u.remark, u.level, u2.name parentName";
        String sql = "FROM user u LEFT JOIN user_group_user ugu ON u.id = ugu.user_id " +
                "INNER JOIN user_group ug ON ugu.user_group_id = ug.id " +
                "LEFT JOIN user u2 ON u.parent_id = u2.id " +
                "WHERE u.state > 0 ";

        if(Integer.parseInt(loginUserLevel) != User.LEVEL_COMPANY){
            sql += "and u.parent_sign like CONCAT('%',?,'%') ";
            param.add(loginUserZzCode);
        }
        if(!StringUtils.isEmpty(zzCode)){
            sql += "and u.zz_code = ? ";
            param.add(zzCode);
        }
        if(!StringUtils.isEmpty(groupId)){
            sql += "and ug.id = ? ";
            param.add(Long.parseLong(groupId));
        }
        if(!StringUtils.isEmpty(state)){
            sql += "and u.state = ? ";
            param.add(Integer.parseInt(state));
        }
        if(!StringUtils.isEmpty(level)){
            sql += "and u.level = ? ";
            param.add(Integer.parseInt(level));
        }
        if(!StringUtils.isEmpty(name)){
            sql += "and u.name like ? ";
            param.add("%"+name+"%");
        }
        sql += "order by u.create_time";

        super.pageSqlQueryByNativeSql(pageInfo, sql.toString(), field, param.toArray());
        return pageInfo;
    }
}
