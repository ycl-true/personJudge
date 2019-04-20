package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.LoginBean;
import com.teacher.judge.demo.bean.Register;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.TokenService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user")
@Api(value = "用户控制器")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    // 用户登录
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "返回用户数据+token")
    public Result login(@RequestBody LoginBean loginBean) {
        if (StringUtils.isEmpty(loginBean.getUserName()) || StringUtils.isEmpty(loginBean.getPassWord())) {
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        // 校验是否存在用户
        User user = userService.findByUserNameAndAndPassword(loginBean.getUserName(), loginBean.getPassWord());
        String token = null;
        if (user != null) {
            // 将以往token置为无效
            tokenService.dropBeforeToken(user.getUserId());
            // 存在则插入token表
            token = tokenService.insertToken(user.getUserId());
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(user, vo);
            vo.setToken(token);
            System.out.println(ApplyUtil.success(vo));
            return ApplyUtil.success(vo);
        } else {
            // 不存在则抛出异常
            throw new TeachException(ResultEnum.USER_NOT_EXIST);
        }
    }

    // 注册 此时无token
    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册", notes = "返回用户数据+token")
    public Result register(@RequestBody Register register) {
        if (StringUtils.isEmpty(register.getUserName()) || StringUtils.isEmpty(register.getPassWord()) || StringUtils.isEmpty(register.getType())) {
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        // 校验是否存在用户
        User user = userService.findByUserName(register.getUserName());
        if (user != null) {
            // 存在代表已经注册过了
            throw new TeachException(ResultEnum.USER_IS_EXIST);
        } else {
            // 不存在则新增用户并插入token
            User u = new User();
            u.setUserName(register.getUserName());
            u.setPassword(DigestUtils.md5DigestAsHex(register.getPassWord().getBytes()));
            u.setValid(Constant.YES.getValue());
            u.setPersonType(ApplyUtil.getPersonType(register.getType()));
            u = userService.save(u);
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(u, vo);
            vo.setToken(tokenService.insertToken(u.getUserId()));
            return ApplyUtil.success(vo);
        }
    }

    // 登出即token置为无效（前提：token有效）
    @DeleteMapping(value = "/loginOut")
    @ApiOperation(value = "用户退出", notes = "返回成功与否")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String")
    public Result loginOut(HttpServletRequest request) {
        String tokenId = request.getHeader("token");
        tokenService.dropToken(tokenId);
        return ApplyUtil.success();
    }


    @GetMapping(value = "/getAllUser")
    public User getAllUser() {
        User user = userService.findById("4028818b6984fde3016984fdf36a0000");
        return user;
    }

    @PostMapping(value = "/getUserVo")
    @ApiOperation(value = "这是一个post", notes = "post_notes")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result getUserVo(@RequestBody UserVo userVo) {
        return ApplyUtil.success(userVo);
    }

    public static void main(String[] args) {
        //827ccb0eea8a706c4c34a16891f84e7b
        System.out.println(DigestUtils.md5DigestAsHex("12345".getBytes()));
    }
}
