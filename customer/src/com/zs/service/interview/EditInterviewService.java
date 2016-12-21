package com.zs.service.interview;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Interview;

/**
 * Created by Allen on 2016/3/9.
 */
public interface EditInterviewService extends EntityService<Interview> {
    public void edit(long id, long linkmanId, String content, String zzCode, String ip, String address)throws Exception;
}
