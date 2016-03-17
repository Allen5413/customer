package com.zs.service.interview.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.DelInterviewByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("delInterviewByIdService")
public class DelInterviewByIdServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements DelInterviewByIdService {

    @Override
    @Transactional
    public void del(String... ids) throws Exception {
        if(ids == null){
            throw new BusinessException("请选择要删除的记录");
        }
        for(String id : ids){
            Interview interview = super.get(Long.parseLong(id));
            if(interview != null){
                super.remove(interview);
            }
        }
    }
}
