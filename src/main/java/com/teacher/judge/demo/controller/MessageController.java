package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.Configs;
import com.teacher.judge.demo.bean.LikeOrDis;
import com.teacher.judge.demo.bean.MessageParam;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.MessageService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.MessageVo;
import com.teacher.judge.demo.vo.UserMsgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/message")
@Api(value = "留言控制器")
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private Configs configs;

    @PostMapping(value = "/message")
    @ApiOperation(value = "评价教师", notes = "提交数据评价教师")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result leaveMessage(@Valid @RequestBody MessageParam messageParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        return ApplyUtil.success(messageService.saveMessage(messageParam));
    }

    @GetMapping(value = "/messages")
    @ApiOperation(value = "获取该教师该课程的留言", notes = "获取该教师该课程的留言")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "teacherId", value = "教师id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码数(从1开始)", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "当前用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "courseId", value = "课程id", required = true, dataType = "String")
    })
    public Result getTeacherMsg(@RequestParam(value = "teacherId") String teacherId,
                                @RequestParam(value = "pageNum") Integer pageNum,
                                @RequestParam(value = "userId") String userId,
                                @RequestParam(value = "courseId") String courseId
    ){
        List<MessageVo> voList = messageService.getAllMsgByTeacherId(teacherId, pageNum,configs.getMsgPageSize(), userId, courseId);
        return ApplyUtil.success(voList);
    }

    @PostMapping(value = "/likeOrDis")
    @ApiOperation(value = "点赞或者反对", notes = "点赞或者反对")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result likeOrDis(@Valid @RequestBody LikeOrDis likeOrDis, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        messageService.updateLikeOrDis(likeOrDis);
        return ApplyUtil.success();
    }

    @GetMapping(value = "/userMessages")
    @ApiOperation(value = "获取该用户所有的留言", notes = "获取该用户所有的留言")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码数(从1开始)", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "当前用户id", required = true, dataType = "String")
    })
    public Result getTeacherMsg(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "userId") String userId
    ){
        List<UserMsgVo> voList = messageService.getAllUserMsgByUserId(pageNum,configs.getMsgPageSize(), userId);
        return ApplyUtil.success(voList);
    }


}
