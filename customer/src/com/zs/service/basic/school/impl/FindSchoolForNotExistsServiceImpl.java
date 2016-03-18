package com.zs.service.basic.school.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.school.FindSchoolForNotExistsDAO;
import com.zs.domain.basic.School;
import com.zs.service.basic.school.FindSchoolForNotExistsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询还没有添加的客户信息
 * Created by Allen on 2016/3/18.
 */
@Service("findSchoolForNotExistsService")
public class FindSchoolForNotExistsServiceImpl extends EntityServiceImpl<School, FindSchoolForNotExistsDAO> implements FindSchoolForNotExistsService {

    @Resource
    private FindSchoolForNotExistsDAO findSchoolForNotExistsDAO;

    @Override
    public List<School> find() {
        return findSchoolForNotExistsDAO.find();
    }
}
