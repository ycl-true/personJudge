package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao extends JpaRepository<Token, String> {
    @Query(value = "update Token set valid = '0' where userId = :userId and valid = '1'")
    @Modifying
    void updateValidOfUser(@Param("userId") String userId);
}
