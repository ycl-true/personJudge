package com.teacher.judge.demo.service;

import com.teacher.judge.demo.bo.UserCourse;
import com.teacher.judge.demo.vo.TeacherCourseVo;

import java.util.List;

public interface UserCourseService {
    List<TeacherCourseVo> getTeacherByUserId(String userId);
    boolean allowJudge(String userId, String courseId, String teacherId);
    List<Object> getTeachersByCourseId(String courseId);
    List<UserCourse> findUserCourseList();
    void deleteById(Integer id);
    UserCourse save(UserCourse userCourse);
}
