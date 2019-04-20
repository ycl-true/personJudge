package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.JudgeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgeInfoDao extends JpaRepository<JudgeInfo, String> {
}
