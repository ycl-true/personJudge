package com.teacher.judge.demo.bo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户
 */
@Entity
@Proxy(lazy = false)
@DynamicUpdate
@Data
public class User {
    /** 用户ID*/
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
    @Column(length = 32)
    private String userId;
    /**用户账号*/
    private String userName;
    /**密码*/
    private String password;
    /**真实姓名*/
    private String nikeName;
    /**人员类型*/
    private String personType;
    /**邮箱*/
    private String email;
    /**用户有效性*/
    private String valid;
    /**电话*/
    private String telphone;
    /**一句话描述*/
    private String description;
    /**头像路径*/
    private String imgUrl;
    /**注册时间*/
    private Date registerDate;

}
