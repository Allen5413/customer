package com.zs.service.interview.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.dao.interviewfile.InterviewFileDAO;
import com.zs.domain.customer.Interview;
import com.zs.domain.customer.InterviewFile;
import com.zs.service.interview.DelInterviewByIdService;
import com.zs.tools.PropertiesTools;
import com.zs.tools.UpLoadFileTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("delInterviewByIdService")
public class DelInterviewByIdServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements DelInterviewByIdService {

    @Resource
    private InterviewFileDAO interviewFileDAO;

    @Override
    @Transactional
    public void del(String realPath, String... ids) throws Exception {
        if(ids == null){
            throw new BusinessException("请选择要删除的记录");
        }
        for(String id : ids){
            Interview interview = super.get(Long.parseLong(id));
            if(interview != null){
                //删除上传附件
                String uploadPath = new PropertiesTools("resource/commons.properties").getProperty("interview.file.pic.url");
                UpLoadFileTools.delDir(realPath+uploadPath+id);
                //删除附件记录
                interviewFileDAO.delByInterviewId(Long.parseLong(id));
                //删除访谈记录
                super.remove(interview);
            }
        }
    }
}
