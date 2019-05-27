package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.dao.UserDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Cacheable(value = "UserGroup")
    @Override
    public Map<String, String> getAllByFlag(String flag) {
        List<User> list = null;
        if(flag.equals(Constant.TEACHER.getValue())){
            list = userDao.findAllByPersonType(flag);
        } else if(flag.equals(Constant.STUDENT.getValue())){
            list = userDao.findAllByValid(Constant.YES.getValue());
        }
        Map<String, String> map = new HashMap<>();
        for (User user: list) {
            map.put(user.getUserId(), "真实姓名:"+user.getNikeName()+",账号:"+user.getUserName());
        }
        return map;
    }

}
