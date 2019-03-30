package com.teacher.judge.demo.aspect;

import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class TokenAspect {

    @Pointcut("execution(public * com.teacher.judge.demo.controller.*.*(..))")
    public void log(){
        // 此处不会执行
    }
    @Before("log()")
    public void doBefore(JoinPoint jp){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String token = request.getHeader("token");
        if(null == token || token.isEmpty()){
            throw new TeachException(ResultEnum.TOKEN_NOT_EXIST);
        }
        log.info("传入token={}",token);
        // 类方法
        log.info("类方法={}",jp.getSignature().getDeclaringTypeName() + ":" + jp.getSignature().getName());
        // 参数
        log.info("参数={}", jp.getArgs().toString());
    }

    @AfterReturning(pointcut = "log()", returning = "object")
    public void doAfter(Object object){
        log.info("返回的参数={}", object);
    }
}
