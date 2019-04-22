package com.teacher.judge.demo.vo;

import lombok.Data;

/**
 * 封装教师的基本信息+所教课程
 */
@Data
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
    /**该老师所教课程*/
    private String courseName;
}
