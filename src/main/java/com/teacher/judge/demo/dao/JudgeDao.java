package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Judge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JudgeDao extends JpaRepository<Judge, String> {
}
