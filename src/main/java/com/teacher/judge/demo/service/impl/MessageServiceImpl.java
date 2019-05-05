package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bean.MessageParam;
import com.teacher.judge.demo.bo.Message;
import com.teacher.judge.demo.dao.MessageDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public void saveMessage(MessageParam messageParam) {
        Message msg = new Message();
        BeanUtils.copyProperties(messageParam, msg);
        msg.setFromId(messageParam.getUserId());
        Date date = new Date();
        msg.setDate(date);
        msg.setValid(Constant.YES.getValue());
        // 直接评论
        if(msg.getMessageType().equals(Constant.MSG_T.getValue())){
            // teacherid fromid content type
            msg.setAgree(0);
            msg.setDisagree(0);
            messageDao.save(msg);
        }
    }
}
