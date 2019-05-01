package com.teacher.judge.demo.bo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 评价课程关系表：学生-课程-老师
 */
@Entity
@Proxy(lazy = false)
@DynamicUpdate
@Data
public class UserCourse {
    /** 用户ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String courseId;
    private String teacherId;

}
