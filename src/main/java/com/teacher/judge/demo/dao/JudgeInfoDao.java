package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.JudgeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeInfoDao extends JpaRepository<JudgeInfo, String> {

    @Query(value = "select info.category_Type, info.answer from judge_info info where info.judge_Id = :judgeId",
            nativeQuery = true)
    List<Object[]> findCategoryAndAnswerByJudgeId(@Param("judgeId") String judgeId);
}
