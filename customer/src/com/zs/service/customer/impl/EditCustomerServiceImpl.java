package com.zs.service.customer.impl;

import com.alibaba.fastjson.JSONObject;
import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.basic.user.FindUserByZZDAO;
import com.zs.dao.cusotmerlog.CustomerLogDAO;
import com.zs.dao.customer.FindCustomerByNameDAO;
import com.zs.dao.customer.FindCustomerByNoDAO;
import com.zs.dao.customer.FindCustomerByUserIdDAO;
import com.zs.dao.customerlinkman.FindLinkmanByCustomerIdDAO;
import com.zs.domain.customer.*;
import com.zs.service.customer.EditCustomerService;
import com.zs.service.customerlinkman.FindLinkmanByCustomerIdService;
import com.zs.service.customerstate.FindCustomerStateService;
import com.zs.service.interview.AddInterviewService;
import com.zs.tools.DateTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/3/8.
 */
@Service("editCustomerService")
public class EditCustomerServiceImpl extends EntityServiceImpl<Customer, FindCustomerByUserIdDAO> implements EditCustomerService {

    @Resource
    private FindLinkmanByCustomerIdDAO findLinkmanByCustomerIdDAO;
    @Resource
    private FindCustomerByNameDAO findCustomerByNameDAO;
    @Resource
    private FindCustomerByNoDAO findCustomerByNoDAO;
    @Resource
    private CustomerLogDAO customerLogDAO;
    @Resource
    private FindUserByZZDAO findUserByZZDAO;
    @Resource
    private FindLinkmanByCustomerIdService findLinkmanByCustomerIdService;
    @Resource
    private AddInterviewService addInterviewService;
    @Resource
    private FindCustomerStateService findCustomerStateService;

    @Override
    @Transactional
    public void edit(Customer customer, String linkmanInfo, String delLinkman, String zzCode, String ip, String address, String loginName) throws Exception {
        if(null == customer){
            throw new BusinessException("没有提交客户信息");
        }
        Customer oldCustomer = super.get(customer.getId());
        if(null == oldCustomer || StringUtils.isEmpty(oldCustomer.getName())){
            throw new BusinessException("该客户不存在");
        }

        if(!StringUtils.isEmpty(customer.getNo())){
            Customer validCustomer = findCustomerByNoDAO.find(customer.getNo().trim().toLowerCase());
            if(null != validCustomer && !validCustomer.getNo().equals(oldCustomer.getNo())){
                throw new BusinessException("学校No："+customer.getNo()+"，已经存在");
            }
        }
        if(!StringUtils.isEmpty(customer.getName())){
            Customer validCustomer = findCustomerByNameDAO.find(customer.getName().trim());
            if(null != validCustomer && !validCustomer.getName().equals(oldCustomer.getName())){
                throw new BusinessException("客户名称："+customer.getName()+"，已经存在");
            }
        }


        //只有指派人、创建人、创建时间是不用更改的
        customer.setUserId(oldCustomer.getUserId());
        customer.setCreator(oldCustomer.getCreator());
        customer.setCreateTime(oldCustomer.getCreateTime());
        customer.setOperator(zzCode);
        customer.setOperateTime(DateTools.getLongNowTime());
        if(oldCustomer.getCustomerStateId() != customer.getCustomerStateId()){
            CustomerState oldState = findCustomerStateService.get(oldCustomer.getCustomerStateId());
            CustomerState newState = findCustomerStateService.get(customer.getCustomerStateId());
            //如果修改了状态，那么记一次拜访记录
            this.addInterviewForUpdateState(customer.getId(), ip, address, zzCode, loginName, oldState.getName(), newState.getName());
        }
        super.update(customer);

        //记录客户信息变更日志
        CustomerLog customerLog = new CustomerLog();
        customerLog.setCustomerId(customer.getId());
        customerLog.setNo(customer.getNo());
        customerLog.setCode(customer.getCode());
        customerLog.setName(customer.getName());
        customerLog.setAddress(customer.getAddress());
        customerLog.setCustomerStateId(customer.getCustomerStateId());
        customerLog.setCustomerTypeId(customer.getCustomerTypeId());
        customerLog.setProvinceCode(customer.getProvinceCode());
        customerLog.setScale(customer.getScale());
        customerLog.setRemark(customer.getRemark());
        customerLog.setOperator(customer.getOperator());
        customerLog.setOperatorName(findUserByZZDAO.find(customer.getOperator()).getName());
        customerLogDAO.save(customerLog);

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
                        String qq = "";
                        String trait = "";
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
                        if(linkmanInfoStrArray.length == 6){
                            id = linkmanInfoStrArray[0];
                            name = linkmanInfoStrArray[1];
                            phone = linkmanInfoStrArray[2];
                            post = linkmanInfoStrArray[3];
                            remark = linkmanInfoStrArray[4];
                            qq = linkmanInfoStrArray[5];
                        }
                        if(linkmanInfoStrArray.length == 7){
                            id = linkmanInfoStrArray[0];
                            name = linkmanInfoStrArray[1];
                            phone = linkmanInfoStrArray[2];
                            post = linkmanInfoStrArray[3];
                            remark = linkmanInfoStrArray[4];
                            qq = linkmanInfoStrArray[5];
                            trait = linkmanInfoStrArray[6];
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
                            customerLankman.setQq(qq);
                            customerLankman.setTrait(trait);
                            customerLankman.setRemark(remark);
                            findLinkmanByCustomerIdDAO.save(customerLankman);
                        }else{
                            CustomerLankman customerLankman = findLinkmanByCustomerIdDAO.get(Long.parseLong(id));
                            customerLankman.setName(name);
                            customerLankman.setOperator(zzCode);
                            customerLankman.setOperateTime(DateTools.getLongNowTime());
                            customerLankman.setPhone(phone);
                            customerLankman.setPost(post);
                            customerLankman.setQq(qq);
                            customerLankman.setTrait(trait);
                            customerLankman.setRemark(remark);
                            findLinkmanByCustomerIdDAO.update(customerLankman);
                        }
                    }
                }
            }
        }
    }


    protected void addInterviewForUpdateState(long customerId, String ip, String address,
                                              String zzCode, String loginName, String oldState, String newState)throws Exception{
        //查询客户联系人信息
        List<JSONObject> linkmanList = findLinkmanByCustomerIdService.findForInterviewCount(customerId);
        if(null != linkmanList && 0 < linkmanList.size()){
            com.alibaba.fastjson.JSONObject json = linkmanList.get(0);
            long linkmanId = Long.parseLong(json.get("id").toString());
            Interview interview = new Interview();
            interview.setCustomerId(customerId);
            interview.setCustomerLankmanId(linkmanId);
            interview.setIp(ip);
            interview.setAddress(address);
            interview.setContent(loginName + "变更状态由\"" + oldState + "\"改为\""+newState+"\"");
            interview.setCreator(zzCode);
            interview.setOperator(zzCode);
            addInterviewService.add(interview, null);
        }
    }
}
