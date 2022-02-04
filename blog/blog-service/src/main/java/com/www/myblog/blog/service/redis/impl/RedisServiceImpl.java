package com.www.myblog.blog.service.redis.impl;

import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constant.RedisCommonContant;
import com.www.common.pojo.dto.security.ScopeDTO;
import com.www.myblog.blog.data.constants.RedisKeyConstant;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>@Description redis的service实现 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/3 23:12 </p>
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {
    /** 博客文章需要保存到redis的字段名称 **/
    private String[] articleFields = {"blogId","userId","title","groupName","blogTag","content","browse","praise","collect","comment","createTime"};
    /** 资源服务id **/
    @Value("${spring.application.name}")
    private String resourceId;



    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    @Override
    public List<ScopeDTO> findUrlScope() {
        return (List<ScopeDTO>) RedisOperation.listGet(RedisCommonContant.URL_SCOPE_PREFIX + resourceId);
    }
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
        String lockKey = RedisKeyConstant.BLOG_ARTICLE_LOCK + articleDTO.getBlogId();
        String value = UUID.randomUUID().toString();
        boolean isOk = true;
        try {
            //添加分布式锁，实现同一时间只有一个现场操作
            if(RedisOperation.lock(lockKey, value,60)){
                //保存到redis中，并设置超时2天
                RedisOperation.hashSet(key,articleDTO,articleFields,2L, TimeUnit.DAYS);
            }
        }catch (Exception e){
            isOk = false;
            log.error("保存博客信息到redis中异常，异常信息：{}",e.getMessage());
        }finally {
            // 释放锁
            RedisOperation.unlock(lockKey,value);
        }
        return isOk;
    }
}
