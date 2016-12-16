package com.zs.service.interview.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.FindInterviewByCustomerIdAndNumService;
import com.zs.service.interview.FindInterviewByWhereService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/16.
 */
@Service("findInterviewByCustomerIdAndNumService")
public class FindInterviewByCustomerIdAndNumServiceImpl extends EntityServiceImpl<Interview, InterviewDAO>
    implements FindInterviewByCustomerIdAndNumService{

    @Resource
    private FindInterviewByWhereService findInterviewByWhereService;

    @Override
    public List<JSONObject> find(long customerId, int num, int loginLevel, int isBrowse, String loginZzCode, long loginId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("customerId", customerId+"");
        map.put("loginLevel", loginLevel+"");
        map.put("isBrowse", isBrowse+"");
        map.put("loginZzCode", loginZzCode);
        map.put("loginId", loginId+"");
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(1);
        pageInfo.setCountOfCurrentPage(num);

        findInterviewByWhereService.findPageByWhere(pageInfo, map);
        return null == pageInfo ? null : pageInfo.getPageResults();
    }
}
