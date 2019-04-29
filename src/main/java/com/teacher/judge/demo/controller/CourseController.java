package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.service.CourseService;
import com.teacher.judge.demo.util.ApplyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/course")
@Api(value = "课程控制器")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/courseMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String")
    })
    public Result getCourseMap(){
        Map<String, String> map = courseService.getAllCourse();
        return ApplyUtil.success(map);
    }
}
