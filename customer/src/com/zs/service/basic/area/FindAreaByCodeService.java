package com.zs.service.basic.area;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.basic.Area;

/**
 * Created by Allen on 2016/3/8.
 */
public interface FindAreaByCodeService extends EntityService<Area> {
    public Area find(String code);
}
