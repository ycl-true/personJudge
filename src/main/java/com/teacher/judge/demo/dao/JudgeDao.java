package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Judge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgeDao extends JpaRepository<Judge, String> {
    int countByUserIdAndCourseIdAndTeachIdAndValid(String userId, String course, String teacherId, String valid);
}
