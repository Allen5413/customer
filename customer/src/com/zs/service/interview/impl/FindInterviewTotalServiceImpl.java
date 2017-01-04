package com.zs.service.interview.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.FindInterviewTotalService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2016/12/29.
 */
@Service("findInterviewTotalService")
public class FindInterviewTotalServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements FindInterviewTotalService {

    @Resource
    private InterviewDAO interviewDAO;

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
    public JSONObject findYear(int year) throws Exception {
        JSONObject json = new JSONObject();
        String date = year+"-01-01 00:00:00";
        String date2 = year+"-12-31 23:59:59";
        List<Object[]> list = interviewDAO.findHalfYearCustomerTotalForMonth(date, date2);
        List<Object[]> list2 = interviewDAO.findHalfYearInterviewTotalForMonth(date, date2);
        int[] customerTotalForMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] interviewTotalForMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        if(null != list && 0 < list.size()){
            for(Object[] objs : list){
                int month = Integer.parseInt(objs[0].toString());
                int count = Integer.parseInt(objs[1].toString());
                customerTotalForMonth[month-1] = count;
            }
        }
        if(null != list2 && 0 < list2.size()){
            for(Object[] objs : list2){
                int month = Integer.parseInt(objs[0].toString());
                int count = Integer.parseInt(objs[1].toString());
                interviewTotalForMonth[month-1] = count;
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

