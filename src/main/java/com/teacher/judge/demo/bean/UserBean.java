package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "用户更新的字段", description = "更新对象json")
public class UserBean {
    @NotEmpty(message="用户id不能为空")
    private String userId;
    /**真实姓名*/
    private String nikeName;
    /**邮箱*/
    @NotEmpty(message="用户邮箱不能为空")
    private String email;
    /**电话*/
    @NotEmpty(message="用户手机号不能为空")
    private String telphone;
    /**一句话描述*/
    @NotEmpty(message="用户自定义描述不能为空")
    private String description;
    private String rePassword;
}
