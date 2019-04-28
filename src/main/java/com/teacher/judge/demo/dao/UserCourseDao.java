package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseDao extends JpaRepository<UserCourse, Integer> {
    List<UserCourse> findAllByUserId(String userId);
    UserCourse findByUserIdAndCourseIdAndTeacherId(String userId, String courseId, String teacherId);
}
