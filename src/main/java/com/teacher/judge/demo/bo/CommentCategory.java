package com.teacher.judge.demo.bo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * 评价大类
 */
@Entity
@Proxy(lazy = false)
@Data
public class CommentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer categoryId;
    private String categoryType;
    private String categoryName;
}
