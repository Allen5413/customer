package com.zs.service.basic.area;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.Area;

import java.util.List;

/**
 * 查询省市
 * Created by Allen on 2016/3/7.
 */
public interface FindAreaForProvinceService extends EntityService<Area> {
    public List<Area> find();
}
