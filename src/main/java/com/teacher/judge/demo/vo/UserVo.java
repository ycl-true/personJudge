package com.teacher.judge.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "用户对象", description = "用户对象信息")
@Data
public class UserVo {

    private String userId;
    /**用户姓名*/
    private String userName;
    /**昵称*/
    private String nikeName;
    /**邮箱*/
    private String email;
    /**token*/
    private String token;
}
