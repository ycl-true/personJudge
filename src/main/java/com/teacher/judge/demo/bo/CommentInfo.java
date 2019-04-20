package com.teacher.judge.demo.bo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 评价细类字典表
 */
@Entity
@Proxy(lazy = false)
@Data
public class CommentInfo {
    @Id
    @Column(length = 3)
    private String commentId;
    private String commentName;
    private String parentType;
    private String valid;
}
