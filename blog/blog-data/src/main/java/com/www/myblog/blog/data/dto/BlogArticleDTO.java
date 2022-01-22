package com.www.myblog.blog.data.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>@Description 博客文章 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:05 </p>
 */
@Data
public class BlogArticleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 博客主键
    */
    private Long blogId;
    /**
     * 博客分组主键
     */
    private Long bgId;
    /**
     * 博客分类主键
     */
    private List<Long> classIds;
    /**
    * 用户ID
    */
    private String userId;
    /**
    * 博客主题
    */
    private String blogTheme;
    /**
    * 博客内容
    */
    private String blogContent;
    /**
    * 博客浏览量
    */
    private Long blogViews;
    /**
    * 博客点赞数
    */
    private Long blogLike;
    /**
    * 博客状态：1有效，2删除
    */
    private String stateCd;
    /**
    * 博客评论数
    */
    private Long blogComment;

}