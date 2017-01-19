package com.zs.service.interview.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.dao.interviewfile.InterviewFileDAO;
import com.zs.domain.customer.Interview;
import com.zs.domain.customer.InterviewFile;
import com.zs.service.interview.EditInterviewService;
import com.zs.tools.DateTools;
import com.zs.tools.PropertiesTools;
import com.zs.tools.UpLoadFileTools;
import com.zs.tools.UserTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("editInterviewService")
public class EditInterviewServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements EditInterviewService {

    @Resource
    private InterviewFileDAO interviewFileDAO;

    @Override
    @Transactional
    public void edit(long id, long linkmanId, String content, String ip, String address, String addFilePaths, String delFilePaths, HttpServletRequest request) throws Exception {
        Interview interview = super.get(id);
        if(null == interview){
            throw new BusinessException("该访谈记录不存在");
        }
        String zzCode = UserTools.getLoginUserForZzCode(request);
        interview.setCustomerLankmanId(linkmanId);
        interview.setContent(content);
        interview.setOperator(zzCode);
        interview.setOperateTime(DateTools.getLongNowTime());
        interview.setIp(ip);
        interview.setAddress(address);
        super.update(interview);

        if(!StringUtils.isEmpty(addFilePaths)){
            String uploadPath = new PropertiesTools("resource/commons.properties").getProperty("interview.file.pic.url");
            uploadPath = uploadPath + interview.getId();
            for(String filePath : addFilePaths.split(",")){
                if(!StringUtils.isEmpty(filePath)) {
                    String fileName = filePath.substring(filePath.lastIndexOf("/"), filePath.length());
                    UpLoadFileTools.custFile(request, filePath, uploadPath, fileName);
                    InterviewFile interviewFile = new InterviewFile();
                    interviewFile.setInterviewId(interview.getId());
                    interviewFile.setType(InterviewFile.TYPE_IMG);
                    interviewFile.setUrl(uploadPath + "/" + fileName);
                    interviewFile.setOperator(zzCode);
                    interviewFileDAO.save(interviewFile);
                }
            }
        }

        if(!StringUtils.isEmpty(delFilePaths)){
            for(String filePath : delFilePaths.split(",")){
                if(!StringUtils.isEmpty(filePath)) {
                    UpLoadFileTools.delFile(request, filePath);
                    interviewFileDAO.delByUrl(filePath);
                }
            }
        }
    }
}
