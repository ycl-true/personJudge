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
 * 评论主表
 */
@Entity
@Proxy(lazy = false)
@DynamicUpdate
@Data
public class Judge {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
    @Column(length = 32)
    private String judgeId;
    private String userId;
    private String teachId;
    private String courseId;
    /**评教类型：学生评教、教师互评、专家评教、教师自评*/
    private String judgeType;
    private String valid;
    private Date createDate;

}
