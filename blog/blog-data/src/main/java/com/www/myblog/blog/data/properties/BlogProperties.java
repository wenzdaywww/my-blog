package com.www.myblog.blog.data.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 项目配置属性类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/22 20:37 </p>
 */
@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "com.www.blog")
public class BlogProperties {
    /** 博客文章浏览量的redis的key前缀 **/
    @Value("${com.www.blog.blog-browse}")
    private String blogBrowse;
    /** 博客文章的redis的key前缀 **/
    @Value("${com.www.blog.blog-article}")
    private String blogArticle;
    /** 博客文章的redis的分布式锁key前缀 **/
    @Value("${com.www.blog.blog-article-lock}")
    private String blogArticleLock;
    /** 资源服务ID的url的scope的redis的key前缀,完整格式： OAUTH2:RESOURCE_ID:URL_SCOPE:资源服务ID **/
    @Value("${com.www.blog.url-scope-prefix}")
    private String urlScopePrefix;
}
