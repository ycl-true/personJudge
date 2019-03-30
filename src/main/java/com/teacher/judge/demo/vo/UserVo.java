package com.teacher.judge.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "用户对象")
@Data
public class UserVo {

    private String userId;
    /**用户姓名*/
    private String userName;
    /**密码*/
    private String password;
    /**昵称*/
    private String nikeName;
    /**人员类型*/
    private String personType;
    /**邮箱*/
    private String email;
    /**用户有效性*/
    private String valid;

    private Date date;
}
