package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:04 </p>
 */
/**
    * 博客文章
    */
@Data
public class BlogArticleEntity implements Serializable {
    /**
    * 博客主键
    */
    private Long blogId;

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
    * 博客状态：1有效，2删除，3封号
    */
    private String stateCd;

    /**
    * 博客评论数
    */
    private Long blogComment;

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