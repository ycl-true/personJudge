package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, String> {
}
