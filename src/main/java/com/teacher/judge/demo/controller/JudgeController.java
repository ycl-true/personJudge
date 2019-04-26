package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.service.JudgeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Api(value = "评论控制器")
@Slf4j
public class JudgeController {
    @Autowired
    private JudgeService judgeService;

    // 1.先验证能不能评价 2再看已经评价过没
//    @PostMapping(value = "/")
//    @ApiOperation(value = "用户登录", notes = "返回用户数据+token")
//    public Result
}
