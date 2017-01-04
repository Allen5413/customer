package com.zs.dao.customer.impl;

import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindListByWhereDAO;
import com.zs.domain.basic.User;
import com.zs.domain.basic.UserGroupResource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页查询客户信息
 * Created by Allen on 2016/3/7.
 */
@Service("findCustomerForInterviewCountByUserIdDAO")
public class FindCustomerForInterviewCountByUserIdDAOImpl extends BaseQueryDao
        implements FindListByWhereDAO {
    @Override
    public List<Object[]> findListByWhere(Map<String, String> paramsMap, Map<String, Boolean> sortMap) {

        List<Object> param = new ArrayList<Object>();
        String userId = paramsMap.get("userId");
        String loginLevel = paramsMap.get("loginLevel");
        String isBrowse = paramsMap.get("isBrowse");
        String loginZzCode = paramsMap.get("loginZzCode");
        String loginId = paramsMap.get("loginId");


        String sql = "select MAX(i.create_time) createDate, c.id, c.name, count(*) count FROM ";
        if(Integer.parseInt(loginLevel) > User.LEVEL_COMPANY) {
            if(Integer.parseInt(isBrowse) == UserGroupResource.ISBROWSE_ME){
                sql += "(select c.* from customer c where c.creator = ? or c.user_id = ?) c ";
                param.add(loginZzCode);
                param.add(loginId);
            }else{
                sql += "(select c.* from user u, customer c where parent_sign like ? and u.id = c.user_id " +
                        "union " +
                        "select c.* from user u, customer c where parent_sign like ? and u.zz_code = c.creator" +
                        ") c ";
                param.add("%"+loginZzCode+"%");
                param.add("%"+loginZzCode+"%");
            }
        }else{
            sql += "customer c ";
        }
        sql += "INNER JOIN interview i on c.id = i.customer_id ";
        if(!StringUtils.isEmpty(userId)){
            sql += "INNER JOIN user u ON c.user_id = u.id ";
            sql += "and u.id = ? ";
            param.add(Long.parseLong(userId));
        }else{
            sql += "LEFT JOIN user u ON c.user_id = u.id ";
        }
        sql += "group by id, name order by i.create_time desc";

        List<Object[]> list = super.sqlQueryByNativeSql(sql.toString(), param.toArray());
        return list;
    }
}
