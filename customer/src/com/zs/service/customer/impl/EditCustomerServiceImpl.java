package com.zs.service.customer.impl;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.customer.FindCustomerByUserIdDAO;
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
    private FindLinkmanByCustomerIdDAO findLinkmanByCustomerIdDAO;

    @Override
    @Transactional
    public void edit(Customer customer, String linkmanInfo, String delLinkman, String zzCode) throws Exception {
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

        //删除联系人
        if(!StringUtils.isEmpty(delLinkman)){
            String[] ids = delLinkman.split(",");
            if(null != ids && 0 < ids.length){
                for(String id : ids){
                    //如果是新增的联系人，马上点击删除操作，页面设置值的时候会设置成new，这种数据就不管
                    if(!"new".equals(id)) {
                        CustomerLankman customerLankman = findLinkmanByCustomerIdDAO.get(Long.parseLong(id));
                        customerLankman.setOperator(zzCode);
                        customerLankman.setOperateTime(DateTools.getLongNowTime());
                        customerLankman.setState(CustomerLankman.STATE_DELETE);
                        findLinkmanByCustomerIdDAO.update(customerLankman);
                    }
                }
            }
        }

        //编辑客户联系人
        if(!StringUtils.isEmpty(linkmanInfo)){
            String[] linkmanInfoArray = linkmanInfo.split("\\|");
            if(null != linkmanInfoArray && 0 < linkmanInfoArray.length){
                for(String linkmanInfoStr : linkmanInfoArray){
                    String[] linkmanInfoStrArray = linkmanInfoStr.split("\\*");
                    if(null != linkmanInfoStrArray && 0 < linkmanInfoStrArray.length){
                        String id = "";
                        String name = "";
                        String phone = "";
                        String post = "";
                        String remark = "";
                        if(linkmanInfoStrArray.length == 1){
                            id = linkmanInfoStrArray[0];
                        }
                        if(linkmanInfoStrArray.length == 2){
                            id = linkmanInfoStrArray[0];
                            name = linkmanInfoStrArray[1];
                        }
                        if(linkmanInfoStrArray.length == 3){
                            id = linkmanInfoStrArray[0];
                            name = linkmanInfoStrArray[1];
                            phone = linkmanInfoStrArray[2];
                        }
                        if(linkmanInfoStrArray.length == 4){
                            id = linkmanInfoStrArray[0];
                            name = linkmanInfoStrArray[1];
                            phone = linkmanInfoStrArray[2];
                            post = linkmanInfoStrArray[3];
                        }
                        if(linkmanInfoStrArray.length == 5){
                            id = linkmanInfoStrArray[0];
                            name = linkmanInfoStrArray[1];
                            phone = linkmanInfoStrArray[2];
                            post = linkmanInfoStrArray[3];
                            remark = linkmanInfoStrArray[4];
                        }

                        //说明是新增的联系人
                        if("new".equals(id)){
                            CustomerLankman customerLankman = new CustomerLankman();
                            customerLankman.setName(name);
                            customerLankman.setCustomerId(customer.getId());
                            customerLankman.setOperator(zzCode);
                            customerLankman.setCreator(zzCode);
                            customerLankman.setState(CustomerLankman.STATE_NORMAL);
                            customerLankman.setPhone(phone);
                            customerLankman.setPost(post);
                            customerLankman.setRemark(remark);
                            findLinkmanByCustomerIdDAO.save(customerLankman);
                        }else{
                            CustomerLankman customerLankman = findLinkmanByCustomerIdDAO.get(Long.parseLong(id));
                            customerLankman.setName(name);
                            customerLankman.setOperator(zzCode);
                            customerLankman.setOperateTime(DateTools.getLongNowTime());
                            customerLankman.setPhone(phone);
                            customerLankman.setPost(post);
                            customerLankman.setRemark(remark);
                            findLinkmanByCustomerIdDAO.update(customerLankman);
                        }
                    }
                }
            }
        }
    }
}
