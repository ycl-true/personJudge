package com.teacher.judge.demo.service;

import com.teacher.judge.demo.bo.User;

public interface UserService {
    /** 查找用户*/
    User findById(String userId);

    /**更新用户信息*/
    boolean updateUserInfo(User user);

    /**通过姓名、密码查找用户*/
    User findByUserNameAndAndPassword(String name, String pass);

    /***/

    /***/
}
