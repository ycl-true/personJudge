package com.teacher.judge.demo.enums;


import lombok.Getter;

@Getter
public enum Constant {
    YES("1"),
    NO("0"),
    // 人员类型
    TEACHER("0"),
    STUDENT("1"),
    PROFESSIONAL("2"),
    // 评价类型
    ST("10"),
    TT("00"),
    PT("20"),
    TS("0+")
    ;
    private String value;
    Constant(String value){
        this.value = value;
    }
}
