package com.zs.dao.interviewfile;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;
import com.zs.domain.customer.InterviewFile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Allen on 2016/12/21.
 */
public interface InterviewFileDAO extends EntityJpaDao<InterviewFile, Long> {

    @Query("from InterviewFile where interviewId = ?1")
    public List<InterviewFile> findByInterviewId(long interviewId)throws Exception;

    @Modifying
    @Query(nativeQuery = true, value = "delete from interview_file where interview_id = ?1")
    public void delByInterviewId(long interviewId)throws Exception;

    @Modifying
    @Query(nativeQuery = true, value = "delete from interview_file where url = ?1")
    public void delByUrl(String url)throws Exception;
}
