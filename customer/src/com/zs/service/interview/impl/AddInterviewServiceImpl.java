package com.zs.service.interview.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.AddInterviewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("addInterviewService")
public class AddInterviewServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements AddInterviewService {

    @Override
    @Transactional
    public void add(Interview interview, String zzCode) throws Exception {
        if(interview == null){
            throw new BusinessException("传入对象为空");
        }
        if(interview.getCustomerId() == 0){
            throw new BusinessException("没有传入客户");
        }
        if(interview.getCustomerLankmanId() == 0){
            throw new BusinessException("没有传入交谈对象");
        }
        if(StringUtils.isEmpty(interview.getContent())){
            throw new BusinessException("没有录入访谈内容");
        }
        if(interview.getContent().length() > 500){
            throw new BusinessException("访谈内容不能超过500字");
        }
        interview.setCreator(zzCode);
        interview.setOperator(zzCode);
        super.save(interview);
    }
}
