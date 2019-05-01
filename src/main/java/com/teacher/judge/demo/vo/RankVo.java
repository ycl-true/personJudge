package com.teacher.judge.demo.vo;

import lombok.Data;

@Data
public class RankVo implements Comparable<RankVo>{
    private String teacherName;
    private Double rankScope;

    public RankVo(String teacherName, Double rankScope) {
        this.teacherName = teacherName;
        this.rankScope = rankScope;
    }

    @Override
    public int compareTo(RankVo rankVo) {
        return this.rankScope.compareTo(rankVo.rankScope);
    }
}
