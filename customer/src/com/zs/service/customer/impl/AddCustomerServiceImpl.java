package com.zs.service.customer.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.school.FindSchoolByNoDAO;
import com.zs.dao.customer.FindCustomerByNameDAO;
import com.zs.dao.customer.FindCustomerByNoDAO;
import com.zs.dao.customer.FindCustomerByUserIdDAO;
import com.zs.dao.customerlinkman.FindLinkmanByCustomerIdDAO;
import com.zs.domain.basic.School;
import com.zs.domain.basic.UserGroupResource;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customer.AddCustomerService;
import com.zs.tools.UserTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/3/7.
 */
@Service("addCustomerService")
public class AddCustomerServiceImpl extends EntityServiceImpl<Customer, FindCustomerByUserIdDAO> implements AddCustomerService {

    @Resource
    private FindCustomerByNoDAO findCustomerByNoDAO;
    @Resource
    private FindLinkmanByCustomerIdDAO findLinkmanByCustomerIdDAO;
    @Resource
    private FindCustomerByNameDAO findCustomerByNameDAO;


    @Override
    @Transactional
    public void add(Customer customer, String linkmanInfo, HttpServletRequest request) throws Exception {
        String zzCode = UserTools.getLoginUserForZzCode(request);
        if(null == customer){
            throw new BusinessException("没有提交客户信息");
        }
        if(!StringUtils.isEmpty(customer.getNo())){
            Customer validCustomer = findCustomerByNoDAO.find(customer.getNo().trim().toLowerCase());
            if(null != validCustomer){
                throw new BusinessException("学校No："+customer.getNo()+"，已经存在");
            }
        }
        if(!StringUtils.isEmpty(customer.getName())){
            Customer validCustomer = findCustomerByNameDAO.find(customer.getName().trim());
            if(null != validCustomer){
                throw new BusinessException("客户名称："+customer.getName()+"，已经存在");
            }
        }

        //查询当前操作用户的管理权限，如果是只能管理自身客户，那么创建后就默认指派给自己，所有客户就不指派
        if(UserTools.getLoginUserForIsAdmin(request) == UserGroupResource.ISADMIN_ME){
            customer.setUserId(UserTools.getLoginUserForId(request));
        }
        customer.setCreator(zzCode);
        customer.setOperator(zzCode);
        super.save(customer);
        //添加客户联系人
        if(!StringUtils.isEmpty(linkmanInfo)){
            String[] linkmanInfoArray = linkmanInfo.split("\\|");
            if(null != linkmanInfoArray && 0 < linkmanInfoArray.length){
                for(String linkmanInfoStr : linkmanInfoArray){
                    String[] linkmanInfoStrArray = linkmanInfoStr.split("\\*");
                    if(null != linkmanInfoStrArray && 0 < linkmanInfoStrArray.length){
                        String name = "";
                        String phone = "";
                        String post = "";
                        String remark = "";
                        if(linkmanInfoStrArray.length == 1){
                            name = linkmanInfoStrArray[0];
                        }
                        if(linkmanInfoStrArray.length == 2){
                            name = linkmanInfoStrArray[0];
                            phone = linkmanInfoStrArray[1];
                        }
                        if(linkmanInfoStrArray.length == 3){
                            name = linkmanInfoStrArray[0];
                            phone = linkmanInfoStrArray[1];
                            post = linkmanInfoStrArray[2];
                        }
                        if(linkmanInfoStrArray.length == 4){
                            name = linkmanInfoStrArray[0];
                            phone = linkmanInfoStrArray[1];
                            post = linkmanInfoStrArray[2];
                            remark = linkmanInfoStrArray[3];
                        }

                        CustomerLankman customerLankman = new CustomerLankman();
                        customerLankman.setCreator(zzCode);
                        customerLankman.setCustomerId(customer.getId());
                        customerLankman.setName(name);
                        customerLankman.setOperator(zzCode);
                        customerLankman.setPhone(phone);
                        customerLankman.setPost(post);
                        customerLankman.setRemark(remark);
                        customerLankman.setState(CustomerLankman.STATE_NORMAL);
                        findLinkmanByCustomerIdDAO.save(customerLankman);
                    }
                }
            }
        }
    }
}
