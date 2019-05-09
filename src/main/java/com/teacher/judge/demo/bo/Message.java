package com.teacher.judge.demo.bo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 留言表
 */
@Entity
@Proxy(lazy = false)
@DynamicUpdate
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String teacherId;
    private String courseId;
    /** 评论类型：0是直接评论 1是回复其他人*/
    private String messageType;
    private String content;
    /** 谁评论的*/
    private String fromId;
    /** 给谁回复的*/
    private String toId;
    /** messageType=1 时，上条评论的id*/
    private Integer parentId;
    private Integer agree;
    private Integer disagree;
    private Date date;
    /** 保存的对应点赞或者讨厌的userId*/
    private String yesOrNo;
    private String valid;
}
