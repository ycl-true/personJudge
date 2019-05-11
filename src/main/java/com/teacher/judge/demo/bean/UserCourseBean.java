package com.teacher.judge.demo.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "权限更新的字段", description = "更新对象json")
public class UserCourseBean {
    private Integer id;
    private String userId;
    private String courseId;
    private String teacherId;
}
