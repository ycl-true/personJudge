package com.teacher.judge.demo.service;

import com.teacher.judge.demo.bo.Token;

public interface TokenService {
    String insertToken(String userId);
    boolean validToken(String tokenId) throws Exception;
    void dropToken(String tokenId);
    Token getOne(String token);
    void dropBeforeToken(String userId);
}
