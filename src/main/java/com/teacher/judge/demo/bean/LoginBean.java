package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录字段", description = "登录对象json")
public class LoginBean {
    @ApiModelProperty(value="用户名" ,required=true)
    private String userName;
    @ApiModelProperty(value="密码" ,required=true)
    private String passWord;
}
