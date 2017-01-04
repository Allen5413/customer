package com.zs.dao.interview;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.Interview;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Allen on 2016/3/9.
 */
public interface InterviewDAO extends EntityJpaDao<Interview, Long> {

    /**
     * 统计一共拜访过多少个客户
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select count(*) from (select DISTINCT i.customer_id from interview i) t")
    public BigInteger findCustomerTotal()throws Exception;

    /**
     * 统计一共拜访有多少条拜访记录
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select count(*) from interview ")
    public BigInteger findInterviewTotal()throws Exception;

    /**
     * 统计半年内，每个月拜访的客户数量
     * @param date
     * @param date2
     * @return
     */
    @Query(nativeQuery = true, value = "select month, count(*) customerCount from (" +
            "select DISTINCT MONTH(i.create_time) month, i.customer_id from interview i where i.create_time >= ?1 and i.create_time <= ?2 " +
            ") t " +
            "group by month")
    public List<Object[]> findHalfYearCustomerTotalForMonth(String date, String date2);

    /**
     * 统计半年内，每个月的拜访数量
     * @param date
     * @param date2
     * @return
     */
    @Query(nativeQuery = true, value = "select MONTH(i.create_time) month, count(*) interviewCount from interview i where i.create_time >= ?1 and i.create_time <= ?2 group by month")
    public List<Object[]> findHalfYearInterviewTotalForMonth(String date, String date2);

    /**
     * 统计月份业务员拜访记录排行
     * @param year
     * @param month
     * @param showNum
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select * from (" +
            "SELECT u.id, u.zz_code, u.name, count(*) num FROM interview i, user u where i.creator = u.zz_code and YEAR(i.create_time) = ?1 and MONTH(i.create_time) = ?2 " +
            "group by u.id, u.zz_code, u.name) t order by t.num desc limit ?3")
    public List<Object[]> findInterviewForUserCount(int year, int month, int showNum)throws Exception;
}
