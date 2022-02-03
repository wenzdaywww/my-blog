package com.www.myblog.blog.service.redis;

import com.www.common.config.redis.RedisOperation;
import com.www.myblog.blog.data.constants.RedisKeyConstant;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>@Description redis的service实现 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/3 23:12 </p>
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService{
    /** 博客文章需要保存到redis的字段名称 **/
    private String[] articleFields = {"blogId","userId","title","groupName","blogTag","content","browse","praise","collect","comment","createTime"};
    /**
     * <p>@Description 从redis中获取博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 23:18 </p>
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.dto.BlogArticleDTO 博客信息
     */
    @Override
    public BlogArticleDTO getArticleInfo(Long blogId) {
        if(blogId == null){
            return new BlogArticleDTO();
        }
        String key = RedisKeyConstant.BLOG_ARTICLE + blogId;
        if(!RedisOperation.hasKey(key)){
            return null;
        }
        //需要保存到redis的字段名称
        BlogArticleDTO articleDTO = RedisOperation.hashGet(key,BlogArticleDTO.class,articleFields);
        return articleDTO;
    }
    /**
     * <p>@Description 保存博客信息到redis中 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 23:18 </p>
     * @param articleDTO 博客id
     * @return true保存成功，false保存失败
     */
    @Override
    public boolean saveArticleInfo(BlogArticleDTO articleDTO) {
        if(articleDTO == null){
            return false;
        }
        String key = RedisKeyConstant.BLOG_ARTICLE + articleDTO.getBlogId();
        //保存到redis中，并设置超时2天
        RedisOperation.hashSet(key,articleDTO,articleFields,2L, TimeUnit.DAYS);
        return true;
    }
}
