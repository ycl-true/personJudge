package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.service.CommentCategoryService;
import com.teacher.judge.demo.util.ApplyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@Api(value = "评价选项控制器")
@Slf4j
public class CommentCategoryController {
    @Autowired
    private CommentCategoryService commentCategoryService;

    // 获取所有的评价类目包括问题
    @GetMapping(value = "/allComment")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String")
    })
    public Result getAllComment(){
        return ApplyUtil.success(commentCategoryService.getAllCommon());
    }


}
