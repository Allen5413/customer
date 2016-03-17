package com.zs.domain.basic;

import com.feinno.framework.common.domain.AbstractEntity;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 用户管理用户组
 * Created by Allen on 2015/5/15.
 */
@Entity
@Table(name = "user_group_user")
public class UserGroupUser extends AbstractEntity {

    public static final int TYPE_TM = 0;
    public static final int TYPE_SYNC = 1;

    private Long id;                            //主键
    private Long userGroupId;                   //用户组id
    private long userId;                        //用户id
    private String creator;                     //创建人
    private Date createTime = new Date();       //创建时间

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
