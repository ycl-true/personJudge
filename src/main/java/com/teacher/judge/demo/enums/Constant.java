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
    ADMIN("3"),
    // 评价类型
    ST("10"),
    TT("00"),
    PT("20"),
    TS("0+"),
    // 留言类型
    MSG_T("0"),
    MSG_U("1"),
    // 点赞动作类型 1点赞 2取消点赞 3踩 4取消踩
    LIKE_1("1"),
    LIKE_2("2"),
    LIKE_3("3"),
    LIKE_4("4"),
    // 点赞状态 1点赞 0是中立 -1是踩
    LIKE_AGREE("1"),
    LIKE_BALANCE("0"),
    LIKE_DISAGREE("-1")
    ;
    private String value;
    Constant(String value){
        this.value = value;
    }
}
