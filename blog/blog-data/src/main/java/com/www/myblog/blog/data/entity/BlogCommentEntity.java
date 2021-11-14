package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 博客评论 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@TableName("BLOG_COMMENT")
public class BlogCommentEntity implements Serializable {
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
    * 评论点赞数
    */
    private Long likeNum;

    /**
    * 评论内容
    */
    private String comment;

    /**
    * 父评论ID
    */
    private Long parentComId;

    /**
    * 更新时间
    */
    private Date sysUpdateTime;

    /**
    * 创建时间
    */
    private Date sysCreateTime;

    private static final long serialVersionUID = 1L;
}