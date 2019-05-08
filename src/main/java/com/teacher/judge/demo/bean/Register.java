package com.teacher.judge.demo.bean;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "注册字段", description = "注册对象json")
public class Register {
    @NotEmpty(message="用户名不能为空")
    private String userName;
    @NotEmpty(message="密码不能为空")
    private String passWord;
    @NotEmpty(message="用户类型不能为空")
    private String type;
}
