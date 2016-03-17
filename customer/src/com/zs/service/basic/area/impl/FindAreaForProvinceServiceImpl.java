package com.zs.service.basic.area.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.area.FindAreaForProvinceDAO;
import com.zs.domain.basic.Area;
import com.zs.service.basic.area.FindAreaForProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/7.
 */
@Service("findAreaForProvinceService")
public class FindAreaForProvinceServiceImpl extends EntityServiceImpl<Area, FindAreaForProvinceDAO> implements FindAreaForProvinceService {

    @Resource FindAreaForProvinceDAO findAreaForProvinceDAO;

    @Override
    public List<Area> find() {
        return findAreaForProvinceDAO.find();
    }
}
