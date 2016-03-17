package com.zs.service.customer;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;

import java.util.Map;

/**
 * Created by Allen on 2016/3/7.
 */
public interface FindCustomerByWhereService extends EntityService {
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> map)throws Exception;
}
