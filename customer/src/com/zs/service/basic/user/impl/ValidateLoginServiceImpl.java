package com.zs.service.basic.user.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.domain.basic.User;
import com.zs.service.basic.user.ValidateLoginService;
import com.zs.tools.HttpRequestTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证用户登录
 * Created by Allen on 2015/4/27.
 */
@Service("validateLoginService")
public class ValidateLoginServiceImpl extends EntityServiceImpl<User, FindUserByZZDAO> implements ValidateLoginService {

    @Resource
    private FindUserByZZDAO findUserByZZDAO;

    @Override
    public User validateLogin(String zzCode, String pwd) throws Exception {
        User user = findUserByZZDAO.find(zzCode);
        if(null == user || StringUtils.isEmpty(user.getName())){
            throw new BusinessException("该用户还没有添加到本系统中");
        }
        return HttpRequestTools.vaildLogin(zzCode, pwd) ? user : null;
        //return HttpRequestTools.vaildLogin(zzCode, pwd) ? user : user;
    }
}
