package com.www.myblog.blog.data.dto;

import lombok.Data;

import java.io.Serializable;
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
    /** 当前页数 **/
    private Integer pageNum;
    /** 页面条数 **/
    private Integer pageSize;
    /**
    * 博客主键
    */
    private Long blogId;
    /**
     * 博客分组主键
     */
    private Long bgId;
    /**
     * 博客分类主键集合
     */
    private List<Long> classIds;
    /**
     * 博客分类主键
     */
    private Long classId;
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
    private Long blogView;
    /**
    * 博客点赞数
    */
    private Long blogLike;
    /**
    * 博客创建时间
    */
    private String createTime;
    /**
    * 博客评论数
    */
    private Long blogComment;
    /**
    * 博客评论数
    */
    private Long blogCollect;

}