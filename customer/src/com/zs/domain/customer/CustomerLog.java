package com.zs.domain.customer;

import com.feinno.framework.common.domain.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户基本资料变更日志
 * Created by Allen on 2016/3/4.
 */
@Entity
@Table(name = "customer_log")
public class CustomerLog extends AbstractEntity {

    private Long id;                            //主键
    private Long customerId;                    //客户id
    private String no;                          //学校No
    private String code;                        //客户代码
    private String name;                        //客户名称
    private String scale;                       //办学规模
    private String provinceCode;                //省份
    private String address;                     //所在地址
    private long customerTypeId;                //客户类型id
    private long customerStateId;               //客户状态id
    private String remark;                      //说明
    private String operator;                    //操作人 ZZ号
    private String operatorName;                //操作人 姓名
    private Date operateTime = new Date();      //操作时间

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public long getCustomerStateId() {
        return customerStateId;
    }

    public void setCustomerStateId(long customerStateId) {
        this.customerStateId = customerStateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
