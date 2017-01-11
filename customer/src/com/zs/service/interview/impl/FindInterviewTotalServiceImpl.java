package com.zs.service.interview.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindListByWhereDAO;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.FindInterviewTotalService;
import com.zs.tools.DateTools;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/29.
 */
@Service("findInterviewTotalService")
public class FindInterviewTotalServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements FindInterviewTotalService {

    @Resource
    private InterviewDAO interviewDAO;
    @Resource
    private FindListByWhereDAO findOneYearCustomerTotalForMonthDAO;
    @Resource FindListByWhereDAO findOneYearInterviewTotalForMonthDAO;

    @Override
    public JSONObject findTotal() throws Exception {
        JSONObject json = new JSONObject();
        BigInteger customerTotal = interviewDAO.findCustomerTotal();
        BigInteger interviewTotal = interviewDAO.findInterviewTotal();
        json.put("customerTotal", customerTotal);
        json.put("interviewTotal", interviewTotal);
        return json;
    }

    @Override
    @Transactional
    public JSONObject findYear(Integer year) throws Exception {
        JSONObject json = new JSONObject();
        String date = "", date2 = "", totalDate= "";
        Map<String, String> paramsMap = new HashMap<String, String>(3);
        if(null != year) {
            date = year + "-1";
            date2 = year + "-12";
            for(int i=1; i<13; i++){
                totalDate += year+"-"+i;
                if(i<12){
                    totalDate += ", ";
                }
            }
        }else{
            String[] last12Month = DateTools.getLast12Months();
            date = last12Month[0];
            date2 = last12Month[11];
            for(int i=0; i<last12Month.length; i++){
                totalDate += last12Month[i];
                if(i<last12Month.length-1){
                    totalDate += ", ";
                }
            }
        }
        paramsMap.put("totalDate", totalDate);
        paramsMap.put("beginDate", date);
        paramsMap.put("endDate", date2);
        List<Object[]> list = findOneYearCustomerTotalForMonthDAO.findListByWhere(paramsMap, null);
        List<Object[]> list2 = findOneYearInterviewTotalForMonthDAO.findListByWhere(paramsMap, null);
        int[] customerTotalForMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] interviewTotalForMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        if(null != list && 0 < list.size()){
            int i=0;
            for(Object[] objs : list){
                int count = Integer.parseInt(objs[1].toString());
                customerTotalForMonth[i] = count;
                i++;
            }
        }
        if(null != list2 && 0 < list2.size()){
            int i=0;
            for(Object[] objs : list2){
                int count = Integer.parseInt(objs[1].toString());
                interviewTotalForMonth[i] = count;
                i++;
            }
        }
        json.put("customerTotalForMonth", customerTotalForMonth);
        json.put("interviewTotalForMonth", interviewTotalForMonth);
        return json;
    }

    @Override
    public List<JSONObject> findInterviewForUserCount(int year, int month, int num) throws Exception {
        List<JSONObject> returnList = new ArrayList<JSONObject>();
        List<Object[]> list = interviewDAO.findInterviewForUserCount(year, month, num);
        if(null != list && 0 < list.size()){
            for(Object[] objs : list){
                JSONObject json = new JSONObject();
                json.put("id", objs[0]);
                json.put("zzCode", objs[1]);
                json.put("name", objs[2]);
                json.put("count", objs[3]);
                returnList.add(json);
            }
        }
        return returnList;
    }
}

