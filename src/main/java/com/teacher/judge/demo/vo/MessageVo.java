package com.teacher.judge.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "留言字段", description = "提交的留言对象")
public class MessageVo {
    private Integer id;
    /** 评论类型：0是直接评论 1是回复其他人*/
    private String messageType;
    private String content;
    /** 谁评论的 */
    private String fromId;
    private String fromName;
    private String fromImgUrl;
    /** 评论人类型*/
    private String personType;
    /** 给谁回复的*/
    private String toName;
    private String toId;
    /** 上一条内容*/
    private String parentContent;
    private Integer agree;
    private Integer disagree;
    private String likedType;
    private Date date;
}
