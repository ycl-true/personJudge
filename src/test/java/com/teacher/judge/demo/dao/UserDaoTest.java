package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getOne() {
        User u = userDao.getOne("123456789012");
        System.out.println(u);
    }

    @Test
//    @Transactional
    public void saveOne() {
        User u = new User();
//        u.setUserId("222222222222");
        u.setUserName("test");
        u.setPassword("1111");
        u.setEmail("1111");
        u.setNikeName("test");
        u.setPersonType("2");
        u.setValid("1");
        userDao.save(u);
    }

    @Test
    public void updateOne(){
        User u = userDao.getOne("4028818b6984fde3016984fdf36a0000");
        u.setPassword("2222");
        userDao.save(u);
    }

    @Test
    public void deleteOne(){
        User u = userDao.getOne("222222222222");
        userDao.delete(u);
    }

    @Test
    public void findByUserIdIn(){
        List<String> ids = Arrays.asList("222222222222","123456789012");
        List<User> u = userDao.findByUserIdIn(ids);
        Assert.assertNotEquals(0,u.size());
    }

    @Test
    public void testException(){
        throw new TeachException(ResultEnum.USER_NOT_EXIST);
    }
}