package com.teacher.judge.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 包含大类和细类
 */
@Data
@ApiModel(value = "评价题目对象", description = "详细评价问题")
public class CommentCategoryVo {
    private String categoryType;
    private String categoryName;
    private List<CommentInfoVo> commentInfoVoList;
}
