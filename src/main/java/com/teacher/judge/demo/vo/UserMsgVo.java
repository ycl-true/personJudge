package com.teacher.judge.demo.vo;

import lombok.Data;

@Data
public class UserMsgVo {
    private Integer id;
    /** 评论类型：0是直接评论 1是回复其他人*/
    private String messageType;
    private String content;
    /** 教师姓名*/
    private String teacherName;
    private String teacherImgUrl;

    private String courseName;
    /** 给谁回复的*/
    private String toName;
    /** 上一条内容*/
//    private String parentContent;
    private String valid;
}
