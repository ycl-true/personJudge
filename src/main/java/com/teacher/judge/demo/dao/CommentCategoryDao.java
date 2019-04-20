package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.CommentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentCategoryDao extends JpaRepository<CommentCategory, Integer> {

}
