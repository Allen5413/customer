package com.zs.domain.basic;

import com.feinno.framework.common.domain.AbstractEntity;

import javax.persistence.*;

/**
 * 学校表
 * Created by Allen on 2016/3/7.
 */
@Entity
@Table(name = "school")
public class School extends AbstractEntity {

    private Long id;                            //主键
    private String code;                        //编号
    private String name;                        //名称

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
