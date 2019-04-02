package com.teacher.judge.demo.enums;


import lombok.Getter;

@Getter
public enum Constant {
    YES("1"),
    NO("0"),
    TEACHER("0"),
    STUDENT("1")
    ;
    private String value;
    Constant(String value){
        this.value = value;
    }
}
