package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.LoginBean;
import com.teacher.judge.demo.bean.Register;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bean.UserBean;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.TokenService;
import com.teacher.judge.demo.service.UserCourseService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.TeacherCourseVo;
import com.teacher.judge.demo.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Api(value = "用户控制器")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserCourseService userCourseService;

    // 用户登录
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "返回用户数据+token")
    public Result login(@Valid @RequestBody LoginBean loginBean, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
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
            if(user.getPersonType().equals(Constant.ADMIN.getValue())){
                vo.setUserName(user.getUserName());
                vo.setUserId(user.getUserId());
            } else {
                BeanUtils.copyProperties(user, vo);
            }
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
    public Result register(@Valid @RequestBody Register register, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        // 校验是否存在用户
        User user = userService.findByUserName(register.getUserName());
        if (user != null && user.getValid() != Constant.YES.getValue()) {
            // 存在代表已经注册过了
            throw new TeachException(ResultEnum.USER_IS_EXIST);
        } else {
            // 不存在则新增用户并插入token
            User u = new User();
            u.setUserName(register.getUserName());
            u.setPassword(DigestUtils.md5DigestAsHex(register.getPassWord().getBytes()));
            u.setValid(Constant.YES.getValue());
            u.setPersonType(ApplyUtil.getPersonType(register.getType()));
            u.setRegisterDate(new Date());
            u.setImgUrl("img/userImag/default.jpg");
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

    // 获取学生所有上课的老师&课程
    @GetMapping(value = "/teachers")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Result teachers(@RequestParam(value = "userId") String userId) {
        List<TeacherCourseVo> list = userCourseService.getTeacherByUserId(userId);
        return ApplyUtil.success(list);
    }

    @PostMapping(value = "/user")
    @ApiOperation(value = "更新用户信息", notes = "返回用户数据")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result updateUser(@Valid @RequestBody UserBean userBean, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        // 查找用户
        User user = userService.findById(userBean.getUserId());
        if (user != null) {
            // 没有传递则沿用旧的
            if(userBean.getNikeName() == null){
                userBean.setNikeName(user.getNikeName());
            }
            BeanUtils.copyProperties(userBean, user);
            if(!StringUtils.isEmpty(userBean.getRePassword())){
                user.setPassword(DigestUtils.md5DigestAsHex(userBean.getRePassword().trim().getBytes()));
            }
            user = userService.save(user);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            return ApplyUtil.success(userVo);
        } else {
            // 不存在则抛出异常
            throw new TeachException(ResultEnum.USER_NOT_EXIST);
        }
    }

    @GetMapping(value = "/users")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "查询的type", required = true, dataType = "String")
    })
    public Result getCourseMap(@RequestParam(value = "type") String type){
        Map<String, String> map = userService.getAllByFlag(type);
        return ApplyUtil.success(map);
    }
}
