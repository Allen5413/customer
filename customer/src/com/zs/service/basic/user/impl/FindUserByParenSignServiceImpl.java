package com.zs.service.basic.user.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByParenSignDAO;
import com.zs.domain.basic.User;
import com.zs.service.basic.user.FindUserByParenSignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/2.
 */
@Service("findUserByParenSignService")
public class FindUserByParenSignServiceImpl extends EntityServiceImpl<User, FindUserByParenSignDAO> implements FindUserByParenSignService {

    @Resource
    private FindUserByParenSignDAO findUserByParenSignDAO;

    @Override
    public List<User> find(String zzCode) {
        return findUserByParenSignDAO.find("%"+zzCode+"%");
    }
}
