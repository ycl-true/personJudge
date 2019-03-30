package com.teacher.judge.demo.handle;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.util.ApplyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof TeachException) {
            TeachException teachException = (TeachException) e;
            return ApplyUtil.getResult(teachException.getCode(), e.getMessage(), null);
        }
        log.error("系统异常 {}", e);
        // 系统异常
        return ApplyUtil.getResult(-1, e.getMessage(), null);
    }
}
