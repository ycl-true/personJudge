package com.teacher.judge.demo.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    TOKEN_NOT_EXIST(403,"非法访问"),
    PARAM_NOT_EXIST(100,"参数不全！"),
    USER_NOT_EXIST(500,"用户不存在！"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
