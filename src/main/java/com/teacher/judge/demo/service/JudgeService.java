package com.teacher.judge.demo.service;

import com.teacher.judge.demo.bean.JudgeMaster;
import com.teacher.judge.demo.bean.RankParam;
import com.teacher.judge.demo.bo.UserCourse;
import com.teacher.judge.demo.vo.RankVo;

import java.util.List;

public interface JudgeService {
    boolean isJudged(String userId, String courseId, String teacherId);
    void saveJudge(JudgeMaster judgeMaster);
    List<RankVo> getRank(RankParam rankParam);
    void deleteJudge(UserCourse userCourse);
}
