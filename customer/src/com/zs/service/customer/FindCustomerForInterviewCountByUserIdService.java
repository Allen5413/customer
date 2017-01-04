package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/1/3.
 */
public interface FindCustomerForInterviewCountByUserIdService extends EntityService {
    public List<JSONObject> find(Map<String, String> parmaMap)throws Exception;
}
