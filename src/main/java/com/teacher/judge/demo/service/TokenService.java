package com.teacher.judge.demo.service;

public interface TokenService {
    String insertToken(String userId);
    boolean validToken(String token);
}
