package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
    Page<Message> findByTeacherIdAndCourseIdAndValid(String teacherId,String courseId, String valid, Pageable pageable);
    Page<Message> findByFromId(String fromId, Pageable pageable);
}
