package com.zs.domain.basic;

import com.feinno.framework.common.domain.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户组关联资源
 * Created by Allen on 2015/4/28.
 */
@Entity
@Table(name = "user_group_resource")
public class UserGroupResource extends AbstractEntity {

    public static final int ISBROWSE_ME = 1;
    public static final int ISBROWSE_ALL = 2;

    public static final int ISADMIN_ME = 1;
    public static final int ISADMIN_ALL = 1;

    public static final int ISASSIGN_NOT = 0;
    public static final int ISASSIGN_YES = 1;


    private Long id;                            //主键
    private Long userGroupId;                   //用户组id
    private Long resourceId;                    //资源ID
    private Integer isBrowse;                       //允许浏览[1：自身客户；2所有客户]
    private Integer isAdmin;                        //允许管理[1：自身客户；2所有客户]
    private Integer isAssign;                       //允许指派[0：不允许；1：允许]
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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    public Integer getIsBrowse() {
        return isBrowse;
    }

    public void setIsBrowse(Integer isBrowse) {
        this.isBrowse = isBrowse;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(Integer isAssign) {
        this.isAssign = isAssign;
    }
}
