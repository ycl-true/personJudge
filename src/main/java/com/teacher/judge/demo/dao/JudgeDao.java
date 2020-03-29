package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Judge;
import com.teacher.judge.demo.dto.JudgeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeDao extends JpaRepository<Judge, String> {
    int countByUserIdAndCourseIdAndTeachIdAndValid(String userId, String course, String teacherId, String valid);

    @Query(value = "select new com.teacher.judge.demo.dto.JudgeDto(jud.teachId, jud.courseId, jud.judgeType, jud.judgeId) from Judge jud where " +
            "jud.teachId = :teacherId and jud.valid = :valid"
            )
    List<JudgeDto> findByTeachIdAndValid(String teacherId, String valid);
    Judge findByUserIdAndCourseIdAndTeachIdAndValid(String userId, String courseId, String teacherId, String valid);
}
