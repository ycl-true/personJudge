package com.teacher.judge.demo.enums;


import lombok.Getter;

@Getter
public enum UserEnum {
    TEACHER("0")
    ;
    private String type;
    UserEnum(String type){
        this.type = type;
    }
}
