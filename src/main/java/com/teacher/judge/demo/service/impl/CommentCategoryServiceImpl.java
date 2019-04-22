package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.dao.CommentCategoryDao;
import com.teacher.judge.demo.dao.CommentInfoDao;
import com.teacher.judge.demo.service.CommentCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class CommentCategoryServiceImpl implements CommentCategoryService {
    @Autowired
    private CommentCategoryDao commentCategoryDao;
    @Autowired
    private CommentInfoDao commentInfoDao;
}
