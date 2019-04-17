package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.dao.UserDao;
import com.teacher.judge.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(String userId) {
        return userDao.getOne(userId);
    }

    @Override
    public boolean updateUserInfo(User user) {
        return false;
    }

    @Override
    public User findByUserNameAndAndPassword(String name, String pass) {
        return userDao.findByUserNameAndAndPassword(name, DigestUtils.md5DigestAsHex(pass.getBytes()));
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
