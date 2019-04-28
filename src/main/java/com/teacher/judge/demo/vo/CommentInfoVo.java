package com.teacher.judge.demo.vo;

import lombok.Data;

@Data
public class CommentInfoVo {
    private String commentId;
    private String commentName;
    // 分数
    private Integer scope;
}
