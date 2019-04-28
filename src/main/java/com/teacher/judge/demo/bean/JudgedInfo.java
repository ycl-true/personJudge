package com.teacher.judge.demo.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class JudgedInfo {
    @NotEmpty(message = "不能没有打分的题目")
    private String commentId;
    // 分数
    @NotNull(message = "不能没有打分的分数")
    private Integer scope;
}
