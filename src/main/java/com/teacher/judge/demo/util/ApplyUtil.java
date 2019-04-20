package com.teacher.judge.demo.util;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;

import java.util.Arrays;

public class ApplyUtil {

    public static Result getResult(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }
    public static Result success(Object data){
        return new Result(ResultEnum.SUCCESS, data);
    }
    public static Result success(){
        return new Result(ResultEnum.SUCCESS);
    }

    public static String getPersonType(String type){
        if(Arrays.asList(Constant.TEACHER.getValue(), Constant.STUDENT.getValue(), Constant.PROFESSIONAL.getValue()
        ).contains(type)){
            return type;
        } else {
            throw new TeachException(ResultEnum.TOKEN_NOT_EXIST);
        }
    }
}
