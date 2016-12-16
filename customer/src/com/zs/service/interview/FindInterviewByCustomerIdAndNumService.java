package com.zs.service.interview;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Interview;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Allen on 2016/12/16.
 */
public interface FindInterviewByCustomerIdAndNumService extends EntityService<Interview>{
    public List<JSONObject> find(long customerId, int num, int loginLevel, int isBrowse, String loginZzCode, long loginId)throws Exception;
}
