package com.teacher.judge.demo.dto;

import lombok.Data;

@Data
public class JudgeDto {
    private String teachId;
    private String courseId;
    private String judgeType;
    // 记录主表id
    private String judgeId;

    // 原有答案
    private String answerA;
    private String answerB;
    private String answerC;
    // 大类答题答题总数
    private Integer totalA;
    private Integer totalB;
    private Integer totalC;
    // 大类问题分数总数
    private Integer targetA;
    private Integer targetB;
    private Integer targetC;
    // 某人对该门教课的某个老师按照所选占比后的得分
    private Double finalScope;

    public JudgeDto(String teachId, String courseId, String judgeType, String judgeId) {
        this.teachId = teachId;
        this.courseId = courseId;
        this.judgeType = judgeType;
        this.judgeId = judgeId;
    }

    public JudgeDto() {
    }
}
