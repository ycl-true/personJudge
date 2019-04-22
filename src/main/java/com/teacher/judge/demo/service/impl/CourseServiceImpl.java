package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.Course;
import com.teacher.judge.demo.dao.CourseDao;
import com.teacher.judge.demo.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    @Cacheable(value = "Course")
    public Map<String, String> getAllCourse(){
        log.info("第一次调用课程字典");
        List<Course> list = courseDao.findAll();
        Map<String, String> map = new HashMap<>();
        for (Course course: list) {
            map.put(course.getCourseId(), course.getCourseName());
        }
        return map;
    }
}
