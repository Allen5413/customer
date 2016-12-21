package com.zs.service.interview;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Interview;

/**
 * Created by Allen on 2016/3/9.
 */
public interface DelInterviewByIdService extends EntityService<Interview> {
    public void del(String realPath, String... ids)throws Exception;
}
