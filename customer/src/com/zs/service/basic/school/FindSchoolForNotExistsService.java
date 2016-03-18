package com.zs.service.basic.school;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.School;

import java.util.List;

/**
 * Created by Allen on 2016/3/18.
 */
public interface FindSchoolForNotExistsService extends EntityService<School> {
    public List<School> find();
}
