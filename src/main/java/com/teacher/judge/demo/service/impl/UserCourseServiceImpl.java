package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.UserCourse;
import com.teacher.judge.demo.dao.UserCourseDao;
import com.teacher.judge.demo.service.CourseService;
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

    // 通过userId查找所能评价的全体老师并封装对象
    @Override
    public List<TeacherCourseVo> getTeacherByUserId(String userId) {
        List<UserCourse> list = userCourseDao.findAllByUserId(userId);
        List<TeacherCourseVo> resultList = new ArrayList<>();
        for (UserCourse uc: list) {
            TeacherCourseVo dto = new TeacherCourseVo();
            BeanUtils.copyProperties(userService.findById(uc.getTeacherId()), dto);
            dto.setCourseName(courseService.getAllCourse().get(uc.getCourseId()));
            resultList.add(dto);
        }
        return resultList;
    }
}
