package com.zs.service.basic.school.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.school.FindSchoolDAO;
import com.zs.domain.basic.School;
import com.zs.service.basic.school.FindSchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
@Service("findSchoolService")
public class FindSchoolServiceImpl extends EntityServiceImpl<School, FindSchoolDAO> implements FindSchoolService {

    @Resource
    private FindSchoolDAO findSchoolDAO;

    @Override
    public List<School> getAll(){
        return findSchoolDAO.findAll();
    }
}
