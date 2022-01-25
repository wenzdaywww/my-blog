package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private static final long serialVersionUID = 1L;
    /**
    * 评论主键
    */
    @TableId(value = "COMMENT_ID",type = IdType.AUTO)
    private Long commentId;
    /**
    * 博客ID
    */
    @TableField("BLOG_ID")
    private Long blogId;
    /**
    * 评论用户ID
    */
    @TableField("USER_ID")
    private String userId;
    /**
    * 评论点赞数
    */
    @TableField("PRAISE")
    private Long praise;
    /**
    * 评论内容
    */
    @TableField("COMMENT")
    private String comment;
    /**
    * 父评论ID
    */
    @TableField("PARENT_COM_ID")
    private Long parentComId;
    /**
    * 更新时间
    */
    @TableField("UPDATE_TIME")
    private Date updateTime;
    /**
    * 创建时间
    */
    @TableField("CREATE_TIME")
    private Date createTime;
}