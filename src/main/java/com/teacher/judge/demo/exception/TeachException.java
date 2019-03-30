package com.teacher.judge.demo.exception;

import com.teacher.judge.demo.enums.ResultEnum;

public class TeachException extends RuntimeException {

    private Integer code;

    public TeachException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public TeachException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
