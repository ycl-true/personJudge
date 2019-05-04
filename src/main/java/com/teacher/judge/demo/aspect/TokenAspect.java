package com.teacher.judge.demo.aspect;

import com.teacher.judge.demo.bean.Configs;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.service.impl.TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class TokenAspect {
    @Autowired
    private TokenServiceImpl tokenServiceImpl;
    @Autowired
    private UserService userService;
    @Autowired
    private Configs configs;

    @Pointcut("execution(public * com.teacher.judge.demo.controller.*.*(..)) " +
            "&& !execution(public * com.teacher.judge.demo.controller.UserController.login(..))" +
            "&& !execution(public * com.teacher.judge.demo.controller.UserController.register(..))")
    public void log(){
        // 此处不会执行
    }
    @Before("log()")
    public void doBefore(JoinPoint jp){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        log.info("访问的类方法={}",jp.getSignature().getDeclaringTypeName() + ":" + jp.getSignature().getName());
        log.info("访问参数={}", jp.getArgs().toString());
        String token = request.getHeader("token");
        if(null == token || token.isEmpty()){
            throw new TeachException(ResultEnum.TOKEN_NOT_EXIST);
        }
        //--需要注意的是当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能的（意思是在方法自身内部调用）
        tokenServiceImpl.test("66666666");
        tokenServiceImpl.test_2("66666666");
        //--
        log.info("传入token={}",token);
        // 验证token有效性
        try{
            if(!tokenServiceImpl.validToken(token)){
                throw new TeachException(ResultEnum.TOKEN_IS_EXPIRE);
            }
        } catch(Exception e) {
            throw new TeachException(ResultEnum.TOKEN_IS_EXPIRE);
        }
        // 拦截用户id
//        log.info("传入的userId={}",getNameAndValue(jp));


    }

    @AfterReturning(pointcut = "log()", returning = "object")
    public void doAfter(Object object){
        log.info("返回的参数={}", object);
        log.info("##########################");
    }

    Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            log.info("参数列表：{}-》{}",paramNames[i],paramValues[i]);
            param.put(paramNames[i], paramValues[i]);
        }

        return param;
    }
}
