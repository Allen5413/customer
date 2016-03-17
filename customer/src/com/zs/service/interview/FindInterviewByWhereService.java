package com.zs.service.interview;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;

import java.util.Map;

/**
 * Created by Allen on 2016/3/9.
 */
public interface FindInterviewByWhereService extends EntityService {
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> map)throws Exception;
}
