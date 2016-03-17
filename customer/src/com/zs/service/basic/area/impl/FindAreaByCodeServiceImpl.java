package com.zs.service.basic.area.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.area.FindAreaByCodeDAO;
import com.zs.domain.basic.Area;
import com.zs.service.basic.area.FindAreaByCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("findAreaByCodeService")
public class FindAreaByCodeServiceImpl extends EntityServiceImpl<Area, FindAreaByCodeDAO> implements FindAreaByCodeService {

    @Resource
    private FindAreaByCodeDAO findAreaByCodeDAO;

    @Override
    public Area find(String code) {
        return findAreaByCodeDAO.find(code);
    }
}
