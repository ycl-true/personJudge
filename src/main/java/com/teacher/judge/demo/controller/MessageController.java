package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.MessageParam;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.MessageService;
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
@RequestMapping(value = "/message")
@Api(value = "留言控制器")
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/message")
    @ApiOperation(value = "评价教师", notes = "提交数据评价教师")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result leaveMessage(@Valid @RequestBody MessageParam messageParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        messageService.saveMessage(messageParam);
        return ApplyUtil.success();
    }
}
