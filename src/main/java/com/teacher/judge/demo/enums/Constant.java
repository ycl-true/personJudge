package com.teacher.judge.demo.enums;


import lombok.Getter;

@Getter
public enum Constant {
    TEACHER("0")
    ;
    private String type;
    Constant(String type){
        this.type = type;
    }
}
