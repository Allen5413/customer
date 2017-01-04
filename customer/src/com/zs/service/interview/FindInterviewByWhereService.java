package com.zs.service.interview;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Interview;

import java.util.Map;

/**
 * Created by Allen on 2016/3/9.
 */
public interface FindInterviewByWhereService extends EntityService<Interview> {
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> map)throws Exception;
}
