package com.teacher.judge.demo.bo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 课程字典表
 */
@Entity
@Proxy(lazy = false)
@Data
public class Course {
    @Id
    private String courseId;
    private String courseName;
}
