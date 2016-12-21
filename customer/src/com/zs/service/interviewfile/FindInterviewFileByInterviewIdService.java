package com.zs.service.interviewfile;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.InterviewFile;

import java.util.List;

/**
 * Created by Allen on 2016/12/21.
 */
public interface FindInterviewFileByInterviewIdService extends EntityService<InterviewFile> {
    public List<InterviewFile> find(long interviewId)throws Exception;
}
