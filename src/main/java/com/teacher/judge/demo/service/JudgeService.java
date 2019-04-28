package com.teacher.judge.demo.service;

import com.teacher.judge.demo.bean.JudgeMaster;

public interface JudgeService {
    boolean isJudged(String userId, String courseId, String teacherId);
    void saveJudge(JudgeMaster judgeMaster);
}
