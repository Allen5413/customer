package com.zs.service.interview;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Interview;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/9.
 */
public interface AddInterviewService extends EntityService<Interview> {
    public void add(Interview interview, HttpServletRequest request)throws Exception;
    public void addForApp(Interview interview, String[] filePaths, HttpServletRequest request)throws Exception;
}
