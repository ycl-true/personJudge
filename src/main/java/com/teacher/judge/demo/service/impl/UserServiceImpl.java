package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.dao.UserDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(String userId) {
        User user = userDao.getOne(userId);
        if(Constant.YES.getValue().equals(user.getValid())){
            return user;
        } else {
            throw new TeachException(ResultEnum.USER_IS_EXPIRE);
        }
    }

    @Override
    public User findById(String userId, String valid) {
        User user = userDao.getOne(userId);
        if(valid.equals(user.getValid())){
            return user;
        } else {
            throw null;
        }
    }

    @Override
    public boolean updateUserInfo(User user) {
        return false;
    }

    @Override
    public User findByUserNameAndAndPassword(String name, String pass) {
        return userDao.findByUserNameAndAndPasswordAndValid(name, DigestUtils.md5DigestAsHex(pass.getBytes()), Constant.YES.getValue());
    }

    @Override

    public User save(User user) {
        log.info("保存/更新用户={}",user);
        return userDao.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }

}
