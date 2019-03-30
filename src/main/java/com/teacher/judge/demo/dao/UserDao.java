package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, String> {
    List<User> findByUserIdIn(List<String> list);
}
