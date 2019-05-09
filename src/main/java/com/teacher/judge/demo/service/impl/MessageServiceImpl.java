package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bean.LikeOrDis;
import com.teacher.judge.demo.bean.MessageParam;
import com.teacher.judge.demo.bo.Message;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.dao.MessageDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.CourseService;
import com.teacher.judge.demo.service.MessageService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.MessageVo;
import com.teacher.judge.demo.vo.UserMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @Override
    public MessageVo saveMessage(MessageParam messageParam) {
        Message msg = new Message();
        BeanUtils.copyProperties(messageParam, msg);
        msg.setFromId(messageParam.getUserId());
        Date date = new Date();
        msg.setDate(date);
        msg.setValid(Constant.YES.getValue());
        msg.setAgree(0);
        msg.setDisagree(0);
        msg.setYesOrNo("{\"like\":[],\"dislike\":[]}");
        // 直接评论
        if(msg.getMessageType().equals(Constant.MSG_T.getValue())){
            // teacherid fromid content type
            msg = messageDao.save(msg);
        } else if(msg.getMessageType().equals(Constant.MSG_U.getValue())){
            // 回复其他人
            msg = messageDao.save(msg);
        }
        return packageMessage(msg, null);
    }

    @Override
    @Transactional(readOnly = true) // 只读事务
    public List<MessageVo> getAllMsgByTeacherId(String teacherId, Integer pageNum, Integer pageLimit, String userId, String courseId) {
        Sort sort = new Sort(Sort.Direction.DESC,"date");
        Pageable pageable =PageRequest.of(pageNum - 1, pageLimit, sort);
        Page<Message> msgPage = messageDao.findByTeacherIdAndCourseIdAndValid(teacherId, courseId, Constant.YES.getValue(), pageable);
        if(!msgPage.hasContent()){
            return null;
        }
        log.info("这页数据={}",msgPage.getNumberOfElements());
        // 拼装数据
        List<MessageVo> voList = new ArrayList<>();
        for (Message msg: msgPage.getContent()) {
            voList.add(packageMessage(msg, userId));
        }
        return voList;
    }

    @Override
    public MessageVo packageMessage(Message msg, String userId){
        MessageVo vo = new MessageVo();
        BeanUtils.copyProperties(msg, vo);
        User user = userService.findById(msg.getFromId());
        vo.setFromName(user.getUserName());
        vo.setPersonType(ApplyUtil.getMsgOfPersonType(user.getPersonType()));
        vo.setFromImgUrl(user.getImgUrl());
        // 如果是回复别人的类型则查询回复的人名+内容
        if (msg.getMessageType().equals(Constant.MSG_U.getValue())){
            vo.setToName(userService.findById(msg.getToId()).getUserName());
            Message parentMsg = messageDao.getOne(msg.getParentId());
            if(parentMsg.getValid().equals(Constant.NO.getValue())){
                vo.setParentContent("该留言内容涉嫌违规，不予展示");
            } else {
                vo.setParentContent(parentMsg.getContent());
            }
        }
        // 有userId代表要判断此留言下该用户的点赞状态
        if(userId != null){
            vo.setLikedType(ApplyUtil.getLikedType(msg.getYesOrNo(), userId));
        }
        return vo;
    }

    @Override
    public void updateLikeOrDis(LikeOrDis likeOrDis) {
        Message msg = messageDao.getOne(likeOrDis.getId());
        // 设置总数
        String type = likeOrDis.getType();
        // 设置点赞人员信息
        // 不完全相信前台的数据，从已有的中总结总数，防止篡改css恶意刷票
        Object[] result = ApplyUtil.getChangedInfo(msg.getYesOrNo(),type,likeOrDis.getUserId());
        msg.setAgree((Integer) result[0]);
        msg.setDisagree((Integer) result[1]);
        msg.setYesOrNo((String) result[2]);
        messageDao.save(msg);
    }

    @Override
    public List<UserMsgVo> getAllUserMsgByUserId(Integer pageNum, Integer pageLimit, String userId) {
        Sort sort = new Sort(Sort.Direction.DESC,"date");
        Pageable pageable =PageRequest.of(pageNum - 1, pageLimit, sort);
        // 查询该用户所有的留言/评论 （包括非法的）
        Page<Message> msgPage = messageDao.findByFromId(userId, pageable);
        if(!msgPage.hasContent()){
            return null;
        }
        log.info("这页数据={}",msgPage.getNumberOfElements());
        // 拼装数据
        List<UserMsgVo> voList = new ArrayList<>();
        for (Message msg: msgPage.getContent()) {
            UserMsgVo vo = new UserMsgVo();
            BeanUtils.copyProperties(msg, vo);
            vo.setCourseName(courseService.getAllCourse().get(msg.getCourseId()));
            User user = userService.findById(msg.getTeacherId());
            vo.setTeacherName(user.getNikeName());
            vo.setTeacherImgUrl(user.getImgUrl());
            if(msg.getMessageType().equals(Constant.MSG_U.getValue())){
                vo.setToName(userService.findById(msg.getToId()).getUserName());
            }
            voList.add(vo);
        }
        return voList;
    }
}
