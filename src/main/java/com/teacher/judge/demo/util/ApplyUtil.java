package com.teacher.judge.demo.util;

import com.teacher.judge.demo.bean.Result;

public class ApplyUtil {

    public static Result getResult(Integer code, String msg, Object data){
        return new Result(code, msg, data);
    }
}
