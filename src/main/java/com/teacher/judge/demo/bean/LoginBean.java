package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "登录字段", description = "登录对象json")
public class LoginBean {
    @ApiModelProperty(value="用户名" ,required=true)
    @NotEmpty(message="用户名不能为空")
    private String userName;
    @ApiModelProperty(value="密码" ,required=true)
    @NotEmpty(message="密码不能为空")
    private String passWord;
}
