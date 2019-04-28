package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(value = "提交的题目对象", description = "提交的评价问题")
public class JudgeMaster {
    @NotEmpty(message="用户id不能为空")
    private String userId;
    @NotEmpty(message="教师id不能为空")
    private String teacherId;
    @NotEmpty(message="课程id不能为空")
    private String courseId;
    @NotEmpty(message="评价列表不能为空")
    @Valid
    private List<Judged> judgedList;
}
