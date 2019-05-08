package com.teacher.judge.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "用户对象", description = "用户对象信息")
@Data
public class UserVo {

    private String userId;
    /**用户姓名*/
    private String userName;
    /**昵称*/
    private String nikeName;
    /**人员类型*/
    private String personType;
    /**邮箱*/
    private String email;
    /**电话*/
    private String telphone;
    /**一句话描述*/
    private String description;
    /**注册时间*/
    private Date registerDate;
    /**头像路径*/
    private String imgUrl;
    /**token*/
    private String token;
}
