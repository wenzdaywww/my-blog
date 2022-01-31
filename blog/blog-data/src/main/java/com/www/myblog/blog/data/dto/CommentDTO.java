package com.www.myblog.blog.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>@Description 博客评论 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@Accessors(chain = true)//开启链式编程
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 当前页数 **/
    private Integer pageNum;
    /**
    * 评论主键
    */
    private Long commentId;
    /**
    * 博客ID
    */
    private Long blogId;
    /**
    * 评论用户ID
    */
    private String userId;
    /**
    * 评论用户名称
    */
    private String userName;
    /**
    * 评论回复的用户Id
    */
    private String replyUserId;
    /**
    * 评论回复的用户名称
    */
    private String replyName;
    /**
    * 评论用户头像
    */
    private String photo;
    /**
    * 评论点赞数
    */
    private Long praise;
    /**
    * 是否打开回复
    */
    private boolean open;
    /**
    * 子评论是否有更多
    */
    private long more;
    /**
    * 评论内容
    */
    private String comment;
    /**
    * 父评论ID
    */
    private Long parentComId;
    /**
    * 创建日期
    */
    private String createDate;
    /** 子评论 **/
    private List<CommentDTO> subList;
}