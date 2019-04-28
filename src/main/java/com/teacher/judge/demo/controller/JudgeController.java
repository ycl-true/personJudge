package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.JudgeMaster;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.JudgeService;
import com.teacher.judge.demo.service.UserCourseService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/judge")
@Api(value = "评论控制器")
@Slf4j
public class JudgeController {
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCourseService userCourseService;

    // 1.先验证能不能评价 2再看已经评价过没 最后再保存
    @PostMapping(value = "/judge")
    @ApiOperation(value = "评价教师", notes = "提交数据评价教师")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result judge(@Valid @RequestBody JudgeMaster judgeMaster, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        System.out.println(judgeMaster);
        //验证userId是否有效
        userService.findById(judgeMaster.getUserId());
        // 验证是否有权限评价
        boolean allowJudge = userCourseService.allowJudge(judgeMaster.getUserId(), judgeMaster.getCourseId(), judgeMaster.getTeacherId());
        if(!allowJudge){
            throw new TeachException(ResultEnum.TOKEN_NOT_EXIST);
        }
        // 验证是否已经评论该课程
        boolean isJudged = judgeService.isJudged(judgeMaster.getUserId(), judgeMaster.getCourseId(), judgeMaster.getTeacherId());
        if(isJudged){
            throw new TeachException(ResultEnum.TOKEN_NOT_EXIST);
        }
        // 符合条件则保存记录
        judgeService.saveJudge(judgeMaster);
        return ApplyUtil.success();
    }
}
