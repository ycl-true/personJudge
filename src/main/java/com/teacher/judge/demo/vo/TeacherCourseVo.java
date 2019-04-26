package com.teacher.judge.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 封装教师的基本信息+所教课程
 */
@Data
@ApiModel(value = "评价的老师信息", description = "评价老师的信息")
public class TeacherCourseVo {
    /**用户昵称*/
    private String userName;
    /**真实姓名*/
    private String nikeName;
    /**邮箱*/
    private String email;
    /**电话*/
    private String telphone;
    /**一句话描述*/
    private String description;
    /**头像路径*/
    private String imgUrl;
    /**教师id*/
    private String teacherId;
    /**该老师所教课程*/
    private String courseName;
    /**课程id*/
    private String courseId;
    /**标志位：是否已经评价了该老师 true 为已评价*/
    public Boolean judgeFlag;
}
