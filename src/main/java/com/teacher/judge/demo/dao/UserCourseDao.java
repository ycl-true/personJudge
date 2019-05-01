package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseDao extends JpaRepository<UserCourse, Integer> {
    List<UserCourse> findAllByUserId(String userId);
    UserCourse findByUserIdAndCourseIdAndTeacherId(String userId, String courseId, String teacherId);

    @Query(value = "select distinct uc.teacher_Id from user_course uc where uc.course_Id = :courseId",
            nativeQuery = true)
    List<Object> findTeachersByCourseId(@Param("courseId") String courseId);
}
