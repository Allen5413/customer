package com.zs.service.interviewfile.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interviewfile.InterviewFileDAO;
import com.zs.domain.customer.InterviewFile;
import com.zs.service.interviewfile.FindInterviewFileByInterviewIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/21.
 */
@Service("findInterviewFileByInterviewIdService")
public class FindInterviewFileByInterviewIdServiceImpl extends EntityServiceImpl<InterviewFile, InterviewFileDAO>
    implements FindInterviewFileByInterviewIdService{

    @Resource
    private InterviewFileDAO interviewFileDAO;

    @Override
    public List<InterviewFile> find(long interviewId) throws Exception {
        return interviewFileDAO.findByInterviewId(interviewId);
    }
}
