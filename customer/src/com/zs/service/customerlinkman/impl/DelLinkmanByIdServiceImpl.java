package com.zs.service.customerlinkman.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customerlinkman.FindLinkmanByCustomerIdDAO;
import com.zs.dao.interview.FindInterviewByLinkManIdDAO;
import com.zs.domain.customer.CustomerLankman;
import com.zs.domain.customer.Interview;
import com.zs.service.customerlinkman.DelLinkmanByIdService;
import com.zs.tools.DateTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("delLinkmanByIdService")
public class DelLinkmanByIdServiceImpl extends EntityServiceImpl<CustomerLankman, FindLinkmanByCustomerIdDAO> implements DelLinkmanByIdService {

    @Resource
    private FindInterviewByLinkManIdDAO findInterviewByLinkManIdDAO;

    @Override
    public void del(long id, String loginName) {
        CustomerLankman customerLankman = super.get(id);
        if(null == customerLankman){
            throw new BusinessException("没有找到该联系人");
        }
        List<Interview> interviewList = findInterviewByLinkManIdDAO.find(id);
        if(null != interviewList && 0 < interviewList.size()){
            throw new BusinessException("该联系人下面已经存在访谈记录，不能被删除");
        }
        customerLankman.setState(CustomerLankman.STATE_DELETE);
        customerLankman.setOperator(loginName);
        customerLankman.setOperateTime(DateTools.getLongNowTime());
        super.update(customerLankman);
    }
}
