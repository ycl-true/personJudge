package com.teacher.judge.demo.service;

import com.teacher.judge.demo.bean.LikeOrDis;
import com.teacher.judge.demo.bean.MessageParam;
import com.teacher.judge.demo.bo.Message;
import com.teacher.judge.demo.vo.MessageVo;
import com.teacher.judge.demo.vo.UserMsgVo;

import java.util.List;

public interface MessageService {
    MessageVo saveMessage(MessageParam messageParam);
    List<MessageVo> getAllMsgByTeacherId(String teacherId, Integer pageNum, Integer pageLimit, String userId, String courseId);
    MessageVo packageMessage(Message msg, String userId);
    void updateLikeOrDis(LikeOrDis likeOrDis);
    List<UserMsgVo> getAllUserMsgByUserId(Integer pageNum, Integer pageLimit, String userId);
}
