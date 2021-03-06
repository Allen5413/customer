package com.zs.dao.customer.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindPageByWhereDAO;
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
        String provinceCode = paramsMap.get("provinceCode");
        String loginLevel = paramsMap.get("loginLevel");
        String isBrowse = paramsMap.get("isBrowse");
        String loginZzCode = paramsMap.get("loginZzCode");
        String loginId = paramsMap.get("loginId");


        String field = "c.id, c.name cname, u.name uname, a.name aname, c.address, c.scale, c.code, c.no, ct.name ctname, cs.name csname, tt.name lname, tt.phone, tt.post, tt.remark, c.user_id";
        String sql = "FROM ";
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
        sql += "LEFT JOIN user u ON c.user_id = u.id " +
            "LEFT JOIN area a ON c.province_code = a.code " +
            "INNER JOIN customer_type ct ON c.customer_type_id = ct.id " +
            "INNER JOIN customer_state cs ON c.customer_state_id = cs.id " +
            "LEFT JOIN (select cl.* from customer_lankman cl, (select min(cl.id) as id, cl.customer_id from customer_lankman cl where cl.state = 0 group by cl.customer_id) t " +
            "where cl.id = t.id and cl.state = 0 and cl.customer_id = t.customer_id) tt ON c.id = tt.customer_id ";
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
        if(!StringUtils.isEmpty(provinceCode)){
            sql += "and c.province_code = ? ";
            param.add(provinceCode);
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
