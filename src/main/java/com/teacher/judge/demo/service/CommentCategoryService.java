package com.teacher.judge.demo.service;

import com.teacher.judge.demo.vo.CommentCategoryVo;

import java.util.List;

public interface CommentCategoryService {
    List<CommentCategoryVo> getAllCommon();
    int getTotalQuestionScope();
}
