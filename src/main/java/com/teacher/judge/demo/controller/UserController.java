package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.ParamCondition;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.impl.UserServiceImpl;
import com.teacher.judge.demo.vo.UserVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    // 用户登录
    @PostMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public User getUsers(@RequestBody ParamCondition paramCondition) {
        if(StringUtils.isEmpty(paramCondition.getUserName()) || StringUtils.isEmpty(paramCondition.getPassWord())){
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        User u = new User();
        u.setPassword(paramCondition.getPassWord());
        u.setUserName(paramCondition.getUserName());
        return u;
    }

    @GetMapping(value = "/getAllUser")
    public User getUsers() {
        User user = userServiceImpl.findById("4028818b6984fde3016984fdf36a0000");
        return user;
    }

    @PostMapping(value = "/getUserVo")
    @ApiOperation(value = "这是一个post",notes = "post_notes")
    @ApiImplicitParam(paramType="header", name = "token", value = "token", required = true, dataType = "String")
    public UserVo getUserVo(@RequestBody UserVo userVo) {
        userVo.setDate(new Date());
        System.out.println(userVo.getDate());
        return userVo;
    }
}
