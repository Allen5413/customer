package com.zs.dao.interview.impl;

import com.zs.dao.BaseQueryDao;
import com.zs.dao.FindListByWhereDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/1/9.
 */
@Service("findOneYearInterviewTotalForMonthDAO")
public class FindOneYearInterviewTotalForMonthDAOImpl extends BaseQueryDao
        implements FindListByWhereDAO {
    @Override
    public List<Object[]> findListByWhere(Map<String, String> paramsMap, Map<String, Boolean> sortMap) {

        List<Object> param = new ArrayList<Object>();
        String[] totalDate = paramsMap.get("totalDate").split(",");
        String beginDate = paramsMap.get("beginDate");
        String endDate = paramsMap.get("endDate");

        String sql = "select ttt.month, ifnull(tt.interviewCount, 0)  from (";
        sql += "SELECT DISTINCT YEAR (i.create_time) year, MONTH (i.create_time) MONTH, count(*) interviewCount ";
        sql += "FROM interview i ";
        sql += "WHERE i.create_time >= ? AND i.create_time <= ? ";
        sql += "GROUP BY year, month) tt ";
        sql += "RIGHT JOIN (";
        for(int i=0; i<totalDate.length; i++){
            String year = totalDate[i].split("-")[0];
            String month = totalDate[i].split("-")[1];
            if(i == totalDate.length - 1){
                sql += "SELECT '" + year + "' year, '" + month + "' month from  DUAL ";
            }else {
                sql += "SELECT '" + year + "' year, '" + month + "' month from  DUAL UNION ALL ";
            }
        }
        sql += ") ttt on tt.month = ttt.month and tt.`year` = ttt.year";

        param.add(beginDate+"-01 00:00:00");
        param.add(endDate+"-31 23:59:59");

        List<Object[]> list = super.sqlQueryByNativeSql(sql.toString(), param.toArray());
        return list;
    }
}
