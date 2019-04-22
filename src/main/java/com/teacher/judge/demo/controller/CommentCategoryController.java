package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.service.CommentCategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@Api(value = "用户控制器")
@Slf4j
public class CommentCategoryController {
    @Autowired
    private CommentCategoryService commentCategoryService;

}
