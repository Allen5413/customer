package com.zs.service.interview;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Interview;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Allen on 2016/12/29.
 */
public interface FindInterviewTotalService extends EntityService<Interview> {
    /**
     * 统计中的拜访客户数和拜访记录总数
     * @return
     * @throws Exception
     */
    public JSONObject findTotal()throws Exception;

    /**
     * 统计半年内的每个月拜访客户数和拜访总数
     * @param year
     * @return
     * @throws Exception
     */
    public JSONObject findYear(Integer year)throws Exception;

    /**
     * 统计月份业务员拜访记录排行
     * @param year
     * @param month
     * @param num
     * @return
     * @throws Exception
     */
    public List<JSONObject> findInterviewForUserCount(int year, int month, int num)throws Exception;
}
