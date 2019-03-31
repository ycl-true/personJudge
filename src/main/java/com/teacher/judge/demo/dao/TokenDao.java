package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao extends JpaRepository<Token, String> {
}
