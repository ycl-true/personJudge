package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "留言字段", description = "提交的留言对象")
public class MessageParam {
    @NotEmpty(message="教师id不能为空")
    private String teacherId;
    @NotEmpty(message="课程id不能为空")
    private String courseId;
    /** 评论类型：0是直接评论 1是回复其他人*/
    @NotEmpty(message="留言类型不能为空")
    private String messageType;
    @NotEmpty(message="留言内容不能为空")
    private String content;
    /** 谁评论的 fromId */
    @NotEmpty(message="用户id不能为空")
    private String userId;
    /** 给谁回复的*/
    private String toId;
    /** messageType=1 时，上条评论的id*/
    private Integer parentId;
}
