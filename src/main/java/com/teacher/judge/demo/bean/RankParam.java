package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "提交的查询对象", description = "提交的查询条件")
public class RankParam {
    @Data
    public class CategoryProp {
        @NotNull
        private Integer A;
        @NotNull
        private Integer B;
        @NotNull
        private Integer C;
    }
    @Data
    public class JudgeProp{
        @NotNull
        private Integer ST;
        @NotNull
        private Integer TT;
        @NotNull
        private Integer TS;
        @NotNull
        private Integer PT;
    }

    @NotEmpty(message="课程id不能为空")
    private String courseId;
    @NotNull
    @Valid
    private CategoryProp categoryProp;
    @NotNull
    @Valid
    private JudgeProp judgeProp;
}
