package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseDao extends JpaRepository<UserCourse, Integer> {
}
