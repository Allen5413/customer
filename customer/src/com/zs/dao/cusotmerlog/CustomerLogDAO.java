package com.zs.dao.cusotmerlog;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.CustomerLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface CustomerLogDAO extends EntityJpaDao<CustomerLog, Long> {

    /**
     * 查询一个客户的信息变更日志
     * @param customerId
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select cl.no, cl.code, cl.name, cl.scale, a.name aName, cl.address, ct.name tName, cs.name sName, cl.remark, cl.operator_name, cl.operate_time " +
            "from customer_log cl, customer_type ct, customer_state cs, area a " +
            "where cl.customer_type_id = ct.id and cl.customer_state_id = cs.id and cl.province_code = a.code and cl.customer_id = ?1 " +
            "order by cl.operate_time desc")
    public List<Object[]> findByCustomerId(long customerId)throws Exception;

    /**
     * 删除一个客户的信息变更日志
     * @param customerId
     * @throws Exception
     */
    @Modifying
    @Query("delete from CustomerLog where customerId = ?1")
    public void delByCustomerId(long customerId)throws Exception;
}
