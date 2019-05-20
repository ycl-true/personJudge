package com.teacher.judge.demo.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200,"成功"),
    TOKEN_IS_EXPIRE(401,"用户消息过期"),
    TOKEN_NOT_EXIST(403,"非法访问"),
    PARAM_NOT_EXIST(100,"参数不全！"),
    USER_NOT_EXIST(500,"账号或密码错误！"),
    USER_IS_EXIST(501,"用户已注册！"),
    USER_IS_EXPIRE(502,"用户已失效"),
    ADMIN_UC_IS_EXIST(600,"此权限已经存在")
    ;
    private Integer code;
    private String msg;
    private Object data;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
