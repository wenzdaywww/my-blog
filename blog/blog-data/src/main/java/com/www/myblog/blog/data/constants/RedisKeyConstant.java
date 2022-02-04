package com.www.myblog.blog.data.constants;

/**
 * <p>@Description redis的key前缀常量
 * key的命名规则：项目名:表名或业务:用于区分key的字段（如主键列名）:主键值
 * </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/14 21:53 </p>
 */
public class RedisKeyConstant {
    /** 博客文章的redis的分布式锁key前缀 **/
    public static final String BLOG_ARTICLE_LOCK = "MY-BLOG:BLOG_ARTICLE:BLOG_ID_LOCK:";
    /** 博客文章的redis的key前缀 **/
    public static final String BLOG_ARTICLE = "MY-BLOG:BLOG_ARTICLE:BLOG_ID:";
    /** 博客文章浏览量的redis的key前缀 **/
    public static final String ARTICLE_BROWSE = "MY-BLOG:BLOG_ARTICLE:BROWSE:BLOG_ID_";
    /** 博客文章点赞量的redis的key前缀 **/
    public static final String ARTICLE_PRAISE = "MY-BLOG:BLOG_ARTICLE:PRAISE:BLOG_ID_";
    /** 博客文章评论量的redis的key前缀 **/
    public static final String ARTICLE_COMMENT = "MY-BLOG:BLOG_ARTICLE:COMMENT:BLOG_ID_";
    /** 博客文章收藏量的redis的key前缀 **/
    public static final String ARTICLE_COLLECT = "MY-BLOG:BLOG_COLLECT:COLLECT:BLOG_ID_";
}
