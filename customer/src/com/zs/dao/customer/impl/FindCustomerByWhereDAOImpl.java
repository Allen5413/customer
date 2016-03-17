package com.zs.dao.customer.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindPageByWhereDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页查询客户信息
 * Created by Allen on 2016/3/7.
 */
@Service("findCustomerByWhereDAO")
public class FindCustomerByWhereDAOImpl extends BaseQueryDao
        implements FindPageByWhereDAO {
    @Override
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> paramsMap, Map<String, Boolean> sortMap) {

        List<Object> param = new ArrayList<Object>();
        String userId = paramsMap.get("userId");
        String typeId = paramsMap.get("typeId");
        String stateId = paramsMap.get("stateId");
        String name = paramsMap.get("name");

        String field = "c.id, c.name cname, u.name uname, a.name aname, c.address, c.scale, c.code, c.no, ct.name ctname, cs.name csname, tt.name lname, tt.phone, tt.post, tt.remark, c.user_id";
        String sql = "FROM customer c LEFT JOIN user u ON c.user_id = u.id " +
                "LEFT JOIN area a ON c.province_code = a.code " +
                "INNER JOIN customer_type ct ON c.customer_type_id = ct.id " +
                "INNER JOIN customer_state cs ON c.customer_state_id = cs.id " +
                "LEFT JOIN (select cl.* from customer_lankman cl, (select min(cl.id) as id, cl.customer_id from customer_lankman cl group by cl.customer_id)t " +
                "where cl.id = t.id and cl.customer_id = t.customer_id) tt ON c.id = tt.customer_id ";
        sql += "where 1=1 ";

        if(!StringUtils.isEmpty(userId)){
            sql += "and u.id = ? ";
            param.add(Long.parseLong(userId));
        }
        if(!StringUtils.isEmpty(typeId)){
            sql += "and ct.id = ? ";
            param.add(Long.parseLong(typeId));
        }
        if(!StringUtils.isEmpty(stateId)){
            sql += "and cs.id = ? ";
            param.add(Long.parseLong(stateId));
        }
        if(!StringUtils.isEmpty(name)){
            sql += "and c.name like ? ";
            param.add("%"+name+"%");
        }
        sql += "order by c.code";

        super.pageSqlQueryByNativeSql(pageInfo, sql.toString(), field, param.toArray());
        return pageInfo;
    }
}
