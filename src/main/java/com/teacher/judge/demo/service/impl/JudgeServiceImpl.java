package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.dao.JudgeDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.JudgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private JudgeDao judgeDao;
    @Override
    public boolean isJudged(String userId, String courseId, String teacherId) {
        // 查找评价主表
        int num = judgeDao.countByUserIdAndCourseIdAndTeachIdAndValid(userId, courseId, teacherId, Constant.YES.getValue());
        log.info("该用户评论主表次数={}", num);
        return num > 0;
    }
}
