package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.impl.TokenServiceImpl;
import com.teacher.judge.demo.service.impl.UserServiceImpl;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/user")
@Api(value = "用户控制器")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    // 用户登录
    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public Result login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        // 校验是否存在用户
        User user = userServiceImpl.findByUserNameAndAndPassword(userName, passWord);
        String token = null;
        if (user != null) {
            // 存在则插入token表
            token = tokenServiceImpl.insertToken(user.getUserId());
            return ApplyUtil.success(token);
        } else {
            // 不存在则抛出异常
            throw new TeachException(ResultEnum.USER_NOT_EXIST);
        }
    }

    // 登出即token置为无效（前提：token有效）
    @GetMapping(value = "/loginOut")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result loginOut(HttpServletRequest request) {
        String tokenId = request.getHeader("token");
        tokenServiceImpl.dropToken(tokenId);
        return ApplyUtil.success();
    }


    @GetMapping(value = "/getAllUser")
    public User getAllUser() {
        User user = userServiceImpl.findById("4028818b6984fde3016984fdf36a0000");
        return user;
    }

    @PostMapping(value = "/getUserVo")
    @ApiOperation(value = "这是一个post", notes = "post_notes")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public UserVo getUserVo(@RequestBody UserVo userVo) {
        userVo.setDate(new Date());
        System.out.println(userVo.getDate());
        return userVo;
    }

    public static void main(String[] args) {
        //827ccb0eea8a706c4c34a16891f84e7b
        System.out.println(DigestUtils.md5DigestAsHex("12345".getBytes()));
    }
}
