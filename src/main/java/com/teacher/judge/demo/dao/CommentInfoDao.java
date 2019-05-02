package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.CommentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentInfoDao extends JpaRepository<CommentInfo, String> {
    List<CommentInfo> findAllByParentType(String parentType);
    int countAllByValid(String valid);
}
