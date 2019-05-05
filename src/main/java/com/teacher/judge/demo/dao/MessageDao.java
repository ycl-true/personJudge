package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
}
