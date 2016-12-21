package com.zs.service.interview.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.EditInterviewService;
import com.zs.tools.DateTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("editInterviewService")
public class EditInterviewServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements EditInterviewService {

    @Override
    @Transactional
    public void edit(long id, long linkmanId, String content, String zzCode, String ip, String address) throws Exception {
        Interview interview = super.get(id);
        if(null == interview){
            throw new BusinessException("该访谈记录不存在");
        }
        interview.setCustomerLankmanId(linkmanId);
        interview.setContent(content);
        interview.setOperator(zzCode);
        interview.setOperateTime(DateTools.getLongNowTime());
        interview.setIp(ip);
        interview.setAddress(address);
        super.update(interview);
    }
}
