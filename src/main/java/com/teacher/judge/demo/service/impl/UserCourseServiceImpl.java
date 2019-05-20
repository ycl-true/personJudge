package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.UserCourse;
import com.teacher.judge.demo.dao.UserCourseDao;
import com.teacher.judge.demo.service.CourseService;
import com.teacher.judge.demo.service.JudgeService;
import com.teacher.judge.demo.service.UserCourseService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.vo.TeacherCourseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    private UserCourseDao userCourseDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private JudgeService judgeService;

    // 通过userId查找所能评价的全体老师并封装对象
    @Override
    public List<TeacherCourseVo> getTeacherByUserId(String userId) {
        List<UserCourse> list = userCourseDao.findAllByUserId(userId);
        List<TeacherCourseVo> resultList = new ArrayList<>();
        for (UserCourse uc: list) {
            TeacherCourseVo vo = new TeacherCourseVo();
            // 封装教师信息
            BeanUtils.copyProperties(userService.findById(uc.getTeacherId()), vo);
            vo.setCourseName(courseService.getAllCourse().get(uc.getCourseId()));
            vo.setJudgeFlag(judgeService.isJudged(userId, uc.getCourseId(), uc.getTeacherId()));
            vo.setCourseId(uc.getCourseId());
            vo.setTeacherId(uc.getTeacherId());
            resultList.add(vo);
        }
        return resultList;
    }

    // 判断是否有权限
    @Override
    public boolean allowJudge(String userId, String courseId, String teacherId) {
        UserCourse userCourse = userCourseDao.findByUserIdAndCourseIdAndTeacherId(userId, courseId, teacherId);
        if(userCourse == null){
            return false;
        }
        return true;
    }

    // 查找课程对应的所有教师id
    @Override
    public List<Object> getTeachersByCourseId(String courseId){
        List<Object> teacherIdList = userCourseDao.findTeachersByCourseId(courseId);
        return teacherIdList;
    }

    @Override
    public List<UserCourse> findUserCourseList() {
        return userCourseDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        UserCourse userCourse = userCourseDao.getOne(id);
        judgeService.deleteJudge(userCourse);
        userCourseDao.deleteById(id);
    }

    @Override
    public UserCourse save(UserCourse userCourse) {
        return userCourseDao.save(userCourse);
    }
}
