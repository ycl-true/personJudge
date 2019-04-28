package com.teacher.judge.demo.bean;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class Judged {
    @NotEmpty(message="主类目不能为空")
    private String categoryType;
    @NotEmpty(message="选项答案不能为空")
    @Valid
    private List<JudgedInfo> judgedInfoList;
}
