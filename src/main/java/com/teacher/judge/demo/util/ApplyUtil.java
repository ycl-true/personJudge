package com.teacher.judge.demo.util;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.enums.ResultEnum;

public class ApplyUtil {

    public static Result getResult(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }
    public static Result success(Object data){
        return new Result(ResultEnum.SUCCESS, data);
    }
}
