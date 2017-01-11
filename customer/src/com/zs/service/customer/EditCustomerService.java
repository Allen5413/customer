package com.zs.service.customer;

import com.feinno.framework.common.service.EntityService;
import com.zs.domain.customer.Customer;

/**
 * Created by Allen on 2016/3/8.
 */
public interface EditCustomerService extends EntityService<Customer> {
    public void edit(Customer customer, String linkmanInfo, String delLinkman, String zzCode, String ip, String address, String loginName)throws Exception;
}
