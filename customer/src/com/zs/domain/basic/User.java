package com.zs.domain.basic;

import com.feinno.framework.common.domain.AbstractEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 自有用户
 * Created by Allen on 2015/4/25.
 */
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    public static final int STATE_DELETE = 0;     //删除
    public static final int STATE_ENABLE = 1;     //启用
    public static final int STATE_DISABLE = 2;    //停用

    private Long id;                            //主键
    private String zzCode;                      //登录zz号
    private String name;                        //姓名
    private String phone;                       //手机
    private Integer state;                      //用户状态
    private String remark;                      //备注
    private String creator;                     //创建人
    private Date createTime = new Date();       //创建时间
    private String operator;                    //操作人
    private Date operateTime = new Date();      //操作时间
    private Integer version;                    //版本号，用于乐观锁

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {return StringUtils.isEmpty(name) ? "" : name;}
    public void setName(String name) {this.name = name;}

    public String getZzCode() {return zzCode;}
    public void setZzCode(String zzCode) {this.zzCode = zzCode;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return StringUtils.isEmpty(remark) ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
