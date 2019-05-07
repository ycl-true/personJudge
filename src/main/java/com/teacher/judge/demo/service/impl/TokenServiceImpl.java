package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bean.Configs;
import com.teacher.judge.demo.bo.Token;
import com.teacher.judge.demo.dao.TokenDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private Configs configs;

    @Override
    public String insertToken(String userId) {
        Token token = new Token();
        token.setUserId(userId);
        token.setLoginDate(new Date());
        token.setValid(Constant.YES.getValue());
        token = tokenDao.save(token);
        log.info("插入的token={}", token.toString());
        return token.getTokenId();
    }

    @Override
    public boolean validToken(String tokenId) throws Exception{
        Token tokenObj = tokenDao.getOne(tokenId);
        long currentTime = System.currentTimeMillis();
        if (tokenObj != null
                && Constant.YES.getValue().equals(tokenObj.getValid())
                && tokenObj.getLoginDate().getTime() + configs.getTimeOut() * 60 * 1000 > currentTime) {
            // token有效且时间没过期，则返回true
            return true;
        }
        return false;
    }

    @Override
    public void dropToken(String tokenId) {
        Token tokenObj = tokenDao.getOne(tokenId);
        tokenObj.setValid(Constant.NO.getValue());
        tokenDao.save(tokenObj);
    }

    @Override
    public Token getOne(String token) {
        return tokenDao.getOne(token);
    }

    @Override
    public void dropBeforeToken(String userId) {
        tokenDao.updateValidOfUser(userId);
    }


    @Cacheable(value = "test",key = "#id")
    public String test(String id){
        System.out.println(id + "--1--" +System.currentTimeMillis());
        return id;
    }
    @Cacheable(value = "test1",key = "#id")
    public String test_2(String id){
        System.out.println(test(id) + "缓存后的--2--" +System.currentTimeMillis());
        return id;
    }
}
