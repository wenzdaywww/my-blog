package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 博客文章 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:05 </p>
 */
@Data
@TableName("BLOG_ARTICLE")
public class BlogArticleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 博客主键
    */
    @TableId(value = "BLOG_ID",type = IdType.AUTO)
    private Long blogId;
    /**
    * 用户ID
    */
    @TableField("USER_ID")
    private String userId;
    /**
    * 博客主题
    */
    @TableField("TITLE")
    private String title;
    /**
    * 博客内容
    */
    @TableField("CONTENT")
    private String content;
    /**
    * 博客浏览量
    */
    @TableField("BROWSE")
    private Long browse;
    /**
    * 博客点赞数
    */
    @TableField("PRAISE")
    private Long praise;
    /**
    * 博客状态：1有效，2删除
    */
    @TableField("STATE_CD")
    private String stateCd;
    /**
    * 博客评论数
    */
    @TableField("COMMENT")
    private Long comment;
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