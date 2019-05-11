package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bean.*;
import com.teacher.judge.demo.bo.Judge;
import com.teacher.judge.demo.bo.JudgeInfo;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.bo.UserCourse;
import com.teacher.judge.demo.dao.JudgeDao;
import com.teacher.judge.demo.dao.JudgeInfoDao;
import com.teacher.judge.demo.dto.JudgeDto;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.CommentCategoryService;
import com.teacher.judge.demo.service.JudgeService;
import com.teacher.judge.demo.service.UserCourseService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.util.MathUtil;
import com.teacher.judge.demo.vo.RankVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private CommentCategoryService commentCategoryService;
    @Autowired
    private Configs configs;

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

    // 组装答案并计算分数，最后排序 -> 所有教该课程的老师的排名
    @Override
    public List<RankVo> getRank(RankParam rankParam){
        List<Object> teachersIdList = userCourseService.getTeachersByCourseId(rankParam.getCourseId());
        List<RankVo> rankVoList = new ArrayList<>();
        // 计算分数
        for (Object obj: teachersIdList) {
            String teacherId = obj.toString();
            String teacherName = userService.findById(teacherId).getNikeName();
            List<JudgeDto> dtoList = getJudgeDtoByTeacherIdAndCourseId(teacherId, rankParam);
            double scope = 0;
            if (dtoList == null){
                // 此课还没有用户评价,返回-1
                scope = -1;
            } else {
                //拿到所有人对该教师的评价分数后，计算按人群比例后的最终rank分数
                scope = getTeacherTotalScope(dtoList, rankParam);
            }
            rankVoList.add(new RankVo(teacherName, scope));
        }
        // 升序排列
        Collections.sort(rankVoList);
        return rankVoList;
    }

    // 权限删除前先把评论主表置为失效
    @Override
    public void deleteJudge(UserCourse userCourse) {
        Judge judge = judgeDao.findByUserIdAndCourseIdAndTeachIdAndValid(userCourse.getUserId(), userCourse.getCourseId(), userCourse.getTeacherId(), Constant.YES.getValue());
        if(judge != null){
            judge.setValid(Constant.NO.getValue());
            judgeDao.save(judge);
        }
    }

    // 填充内容,返回所有人对此老师课程的评价对象（一个JudgeDto代表一个用户评价了此老师教的此课）
    public List<JudgeDto> getJudgeDtoByTeacherIdAndCourseId(String teacherId, RankParam rankParam){
        List<JudgeDto> dtoList = judgeDao.findByTeachIdAndCourseIdAndValid(teacherId, rankParam.getCourseId(), Constant.YES.getValue());
        if(dtoList != null && !dtoList.isEmpty()){
            for(JudgeDto dto : dtoList){
                // 计算各个大类的相关分数
                List<Object[]> infoList = judgeInfoDao.findCategoryAndAnswerByJudgeId(dto.getJudgeId());
                // 所有的提问总分
                int questionTotal = 0;
                for (Object[] info: infoList) {
                    String answerJson = info[1].toString();
                    String category = info[0].toString().trim();
                    int[] array = ApplyUtil.getScope(answerJson, null);
                    questionTotal += array[1];
                    if (category.equals("A")) {
                        dto.setAnswerA(answerJson);
                        dto.setTotalA(array[0]);
                        dto.setTargetA(array[1]);
                    } else if (category.equals("B")) {
                        dto.setAnswerB(answerJson);
                        dto.setTotalB(array[0]);
                        dto.setTargetB(array[1]);
                    } else if (category.equals("C")) {
                        dto.setAnswerC(answerJson);
                        dto.setTotalC(array[0]);
                        dto.setTargetC(array[1]);
                    }
                }
                // 计算某条评价按照相应大类比例后的总分数
                // 计算公式：假设A类评价分13，总分30，用户选择33%---B类评价分63，总分90，用户选择33%---C类评价分13，总分30，用户选择34%
                // = 150*((13/30)*33% + (63/90)*33% + (13/30)*34%)
                RankParam.CategoryProp prop = rankParam.getCategoryProp();
                dto.setFinalScope(MathUtil.div(questionTotal, 100) * (MathUtil.div(dto.getTotalA() * prop.getA(), dto.getTargetA()) +
                        MathUtil.div(dto.getTotalB() * prop.getB(), dto.getTargetB()) +
                        MathUtil.div(dto.getTotalC() * prop.getC(), dto.getTargetC())));
            }
        } else {
            // 还未评价
            return null;
        }
        log.info("******* dtoList={}",dtoList);
        log.info("******* dtoListSize={}",dtoList.size());
        return dtoList;
    }

    // 计算按人群比例后的最终rank分数
    public double getTeacherTotalScope(List<JudgeDto> dtoList, RankParam rankParam){
        RankParam.JudgeProp judgeProp = rankParam.getJudgeProp();
        double[] scope = new double[4];
        int[] typeCount = new int[4];
        for(JudgeDto dto: dtoList){
            if(dto.getJudgeType().equals(Constant.ST.getValue())){
                scope[0] += dto.getFinalScope();
                typeCount[0] += 1;
            } else if(dto.getJudgeType().equals(Constant.TT.getValue())){
                scope[1] += dto.getFinalScope();
                typeCount[1] += 1;
            } else if(dto.getJudgeType().equals(Constant.PT.getValue())){
                scope[2] += dto.getFinalScope();
                typeCount[2] += 1;
            } else if(dto.getJudgeType().equals(Constant.TS.getValue())){
                scope[3] += dto.getFinalScope();
                typeCount[3] += 1;
            }
        }
        // 计算方式
        // 每个群体的平均分*对应比例加起来就是最终得分
        // 如果某个群体没评价，则用占总分的configs.proportion比例假的分数代替
        double finalScope = 0;
        double temp = commentCategoryService.getTotalQuestionScope() * 10 * configs.getProportion();
        log.info("题干假的所得分={}",temp);
        if (typeCount[0] != 0) {
            finalScope += MathUtil.div(scope[0] * judgeProp.getST(), typeCount[0]);
        } else {
            finalScope += temp * judgeProp.getST();
        }
        if (typeCount[1] != 0) {
            finalScope += MathUtil.div(scope[1] * judgeProp.getTT(), typeCount[1]);
        } else {
            finalScope += temp * judgeProp.getTT();
        }
        if (typeCount[2] != 0) {
            finalScope += MathUtil.div(scope[2] * judgeProp.getPT(), typeCount[2]);
        } else {
            finalScope += temp * judgeProp.getPT();
        }
        if (typeCount[3] != 0) {
            finalScope += MathUtil.div(scope[3] * judgeProp.getTS(), typeCount[3]);
        } else {
            finalScope += temp * judgeProp.getTS();
        }
        return MathUtil.div(finalScope, 100);
    }

}
