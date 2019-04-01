package com.teacher.judge.demo.bean;

import com.teacher.judge.demo.enums.ResultEnum;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Result(ResultEnum resultEnum, Object o){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = (T) o;
    }
    public Result(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }
}
