package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseDao extends JpaRepository<UserCourse, Integer> {
    List<UserCourse> findAllByUserId(String userId);
}
