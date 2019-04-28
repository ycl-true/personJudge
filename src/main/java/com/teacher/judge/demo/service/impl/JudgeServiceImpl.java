package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bean.JudgeMaster;
import com.teacher.judge.demo.bean.Judged;
import com.teacher.judge.demo.bean.JudgedInfo;
import com.teacher.judge.demo.bo.Judge;
import com.teacher.judge.demo.bo.JudgeInfo;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.dao.JudgeDao;
import com.teacher.judge.demo.dao.JudgeInfoDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.JudgeService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private JudgeDao judgeDao;
    @Autowired
    private JudgeInfoDao judgeInfoDao;
    @Autowired
    private UserService userService;

    @Override
    public boolean isJudged(String userId, String courseId, String teacherId) {
        // 查找评价主表
        int num = judgeDao.countByUserIdAndCourseIdAndTeachIdAndValid(userId, courseId, teacherId, Constant.YES.getValue());
        log.info("该用户评论主表次数={}", num);
        return num > 0;
    }

    @Override
    public void saveJudge(JudgeMaster judgeMaster) {
        // 同时插入主表和详情表
        User teacher = userService.findById(judgeMaster.getTeacherId());
        User judgePerson = userService.findById(judgeMaster.getUserId());

        Judge judge = new Judge();
        judge.setUserId(judgeMaster.getUserId());
        judge.setTeachId(judgeMaster.getTeacherId());
        judge.setCourseId(judgeMaster.getCourseId());
        judge.setJudgeType(ApplyUtil.getJudgeType(judgePerson,teacher));
        judge.setValid(Constant.YES.getValue());
        judge.setCreateDate(new Date());
        judge = judgeDao.save(judge);

        List<Judged> judgedList = judgeMaster.getJudgedList();
        for (Judged judged : judgedList) {
            JudgeInfo daoInfo = new JudgeInfo();
            daoInfo.setJudgeId(judge.getJudgeId());
            daoInfo.setCategoryType(judged.getCategoryType());
            // 组合成json答案
            StringBuilder sb = new StringBuilder("{");
            List<JudgedInfo> judgedInfoList = judged.getJudgedInfoList();
            for (JudgedInfo judgedInfo:judgedInfoList) {
                sb.append("\"").append(judgedInfo.getCommentId()).append("\":");
                sb.append(judgedInfo.getScope()).append(",");
            }
            daoInfo.setAnswer(sb.substring(0,sb.length()-1)+"}");
            judgeInfoDao.save(daoInfo);
        }
    }

}
