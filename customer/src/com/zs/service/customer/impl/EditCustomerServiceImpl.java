package com.zs.service.customer.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerByUserIdDAO;
import com.zs.dao.customerlinkman.DelLinkmanByCustomerIdDAO;
import com.zs.dao.customerlinkman.FindLinkmanByCustomerIdDAO;
import com.zs.domain.customer.Customer;
import com.zs.domain.customer.CustomerLankman;
import com.zs.service.customer.EditCustomerService;
import com.zs.tools.DateTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("editCustomerService")
public class EditCustomerServiceImpl extends EntityServiceImpl<Customer, FindCustomerByUserIdDAO> implements EditCustomerService {

    @Resource
    private DelLinkmanByCustomerIdDAO delLinkmanByCustomerIdDAO;
    @Resource
    private FindLinkmanByCustomerIdDAO findLinkmanByCustomerIdDAO;

    @Override
    @Transactional
    public void edit(Customer customer, String linkmanInfo, String zzCode) throws Exception {
        if(null == customer){
            throw new BusinessException("没有提交客户信息");
        }
        //验证该客户是否已经添加过了
        Customer validCustomer = super.get(customer.getId());
        if(null == validCustomer || StringUtils.isEmpty(validCustomer.getName())){
            throw new BusinessException("该客户不存在");
        }
        //只有no、名称、创建人、创建时间是不能更改的
        customer.setNo(validCustomer.getNo());
        customer.setName(validCustomer.getName());
        customer.setCreator(validCustomer.getCreator());
        customer.setCreateTime(validCustomer.getCreateTime());


        customer.setOperator(zzCode);
        customer.setOperateTime(DateTools.getLongNowTime());
        super.update(customer);
        //先删掉之前添加的联系人，重新再添加过联系人
        delLinkmanByCustomerIdDAO.del(customer.getId());
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
                        findLinkmanByCustomerIdDAO.save(customerLankman);
                    }
                }
            }
        }
    }
}
