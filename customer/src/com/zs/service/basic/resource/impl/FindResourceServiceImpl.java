package com.zs.service.basic.resource.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.resource.ResourceDAO;
import com.zs.domain.basic.Resource;
import com.zs.service.basic.resource.FindResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现查询资源信息接口
 * Created by Allen on 2015/4/27.
 */
@Service("findResourceService")
public class FindResourceServiceImpl extends EntityServiceImpl<Resource, ResourceDAO> implements FindResourceService {

    @javax.annotation.Resource
    private ResourceDAO resourceDAO;

    @Override
    public List<Resource> getAll(){
        return resourceDAO.findAll();
    }
}
