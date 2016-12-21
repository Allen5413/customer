package com.zs.dao.interview.impl;

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
 * 分页查询访谈信息
 * Created by Allen on 2016/3/7.
 */
@Service("findInterviewByWhereDAO")
public class FindInterviewByWhereDAOImpl extends BaseQueryDao
        implements FindPageByWhereDAO {
    @Override
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> paramsMap, Map<String, Boolean> sortMap) {

        List<Object> param = new ArrayList<Object>();
        String customerId = paramsMap.get("customerId");
        String userId = paramsMap.get("userId");
        String typeId = paramsMap.get("typeId");
        String stateId = paramsMap.get("stateId");
        String beginDate = paramsMap.get("beginDate");
        String endDate = paramsMap.get("endDate");
        String loginLevel = paramsMap.get("loginLevel");
        String isBrowse = paramsMap.get("isBrowse");
        String loginZzCode = paramsMap.get("loginZzCode");
        String loginId = paramsMap.get("loginId");

        String field = "i.id, u.name uName, c.name cName, cl.name clName, cs.name csName, ct.name ctName, i.content, i.ip, i.address, i.operate_time";
        String sql = "from ";

        if(Integer.parseInt(loginLevel) > User.LEVEL_COMPANY) {
            if(Integer.parseInt(isBrowse) == UserGroupResource.ISBROWSE_ME){
                sql += "(SELECT i.* FROM interview i WHERE i.creator = ?) i, ";
                sql += "(select c.* from customer c where c.creator = ? or c.user_id = ?) c, ";
                param.add(loginZzCode);
                param.add(loginZzCode);
                param.add(loginId);
            }else{
                sql += "(SELECT i.* FROM interview i, user u WHERE i.creator = u.zz_code and u.parent_sign like ?) i, ";
                sql += "(select c.* from user u, customer c where parent_sign like ? and u.id = c.user_id " +
                        "union " +
                        "select c.* from user u, customer c where parent_sign like ? and u.zz_code = c.creator" +
                        ") c, ";
                param.add("%"+loginZzCode+"%");
                param.add("%"+loginZzCode+"%");
                param.add("%"+loginZzCode+"%");
            }
        }else{
            sql += "interview i, customer c, ";
        }


        sql += "customer_lankman cl, user u, customer_state cs, customer_type ct ";
        sql += "where i.customer_id = c.id and i.customer_lankman_id = cl.id and i.creator = u.zz_code and c.customer_state_id = cs.id and c.customer_type_id = ct.id ";

        if(!StringUtils.isEmpty(customerId)){
            sql += "and i.customer_id = ? ";
            param.add(Long.parseLong(customerId));
        }
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
        if(!StringUtils.isEmpty(beginDate)){
            sql += "and i.operate_time >= ? ";
            param.add(beginDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            sql += "and i.operate_time <= ? ";
            param.add(endDate+" 23:59:59");
        }
        sql += "order by i.operate_time desc";

        super.pageSqlQueryByNativeSql(pageInfo, sql.toString(), field, param.toArray());
        return pageInfo;
    }
}
