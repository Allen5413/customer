package com.zs.service.interview.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.interview.InterviewDAO;
import com.zs.dao.interviewfile.InterviewFileDAO;
import com.zs.domain.customer.Interview;
import com.zs.domain.customer.InterviewFile;
import com.zs.service.interview.AddInterviewService;
import com.zs.tools.PropertiesTools;
import com.zs.tools.UpLoadFileTools;
import com.zs.tools.UserTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("addInterviewService")
public class AddInterviewServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements AddInterviewService {

    @Resource
    private InterviewFileDAO interviewFileDAO;

    @Override
    @Transactional
    public void add(Interview interview, HttpServletRequest request) throws Exception {
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
        String zzCode = "";
        if(null != request) {
            zzCode = UserTools.getLoginUserForZzCode(request);
            interview.setCreator(zzCode);
            interview.setOperator(zzCode);
        }
        super.save(interview);

        if(null != request) {
            MultipartRequest mulReu = (MultipartRequest) request;
            String uploadPath = new PropertiesTools("resource/commons.properties").getProperty("interview.file.pic.url");

            //处理上传图片
            String imgUrls = UpLoadFileTools.uploadImg(request, mulReu.getFiles("img"), "jpg|png|jpeg", 10240, 10, uploadPath + interview.getId());
            if (!StringUtils.isEmpty(imgUrls)) {
                String[] imgUrlArray = imgUrls.split(",");
                if (null != imgUrlArray && 0 < imgUrlArray.length) {
                    for (String imgUrl : imgUrlArray) {
                        InterviewFile interviewFile = new InterviewFile();
                        interviewFile.setInterviewId(interview.getId());
                        interviewFile.setType(InterviewFile.TYPE_IMG);
                        interviewFile.setUrl(imgUrl);
                        interviewFile.setOperator(zzCode);
                        interviewFileDAO.save(interviewFile);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void addForApp(Interview interview, String[] filePaths, HttpServletRequest request) throws Exception {
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
        String zzCode = "";
        if(null != request) {
            zzCode = UserTools.getLoginUserForZzCode(request);
            interview.setCreator(zzCode);
            interview.setOperator(zzCode);
        }
        super.save(interview);

        if(null != filePaths) {
            String uploadPath = new PropertiesTools("resource/commons.properties").getProperty("interview.file.pic.url");
            uploadPath = uploadPath + interview.getId();
            for(String filePath : filePaths){
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
    }
}
