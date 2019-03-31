package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bean.Configs;
import com.teacher.judge.demo.bo.Token;
import com.teacher.judge.demo.dao.TokenDao;
import com.teacher.judge.demo.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        token = tokenDao.save(token);
        log.info("插入的token={}", token.toString());
        return token.getTokenId();
    }

    @Override
    public boolean validToken(String token) {
        Token tokenObj = tokenDao.getOne(token);
        long cureentTime = System.currentTimeMillis();
        log.info("配置毫秒={}",configs.getTimeOut() * 60 * 1000);
        log.info("数据库时间={}",tokenObj.getLoginDate().getTime());
        log.info("时间范围={}",tokenObj.getLoginDate().getTime() + configs.getTimeOut() * 60 * 1000);
        log.info("当前时间={}",cureentTime);
        if(tokenObj != null && tokenObj.getLoginDate().getTime() + configs.getTimeOut() * 60 * 1000 > cureentTime){
            // 时间没过期，则返回true
            return true;
        }
        return false;
    }
}
