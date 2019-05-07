package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "点赞或者反对字段", description = "提交的对象")
public class LikeOrDis {
    @NotEmpty(message="用户id不能为空")
    private String userId;
    @NotNull(message="留言id不能为空")
    private Integer id;
    @NotEmpty(message="类型不能为空")
    /** 三种: 1是要赞 2是取消赞 3是要踩 4是取消踩*/
    private String type;
}
