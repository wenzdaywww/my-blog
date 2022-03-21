package com.www.myblog.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constant.CharConstant;
import com.www.common.utils.DateUtils;
import com.www.common.utils.NumberUtils;
import com.www.myblog.common.dto.BlogArticleDTO;
import com.www.myblog.task.data.constants.RedisKeyConstant;
import com.www.myblog.task.data.entity.blog.BlogArticleEntity;
import com.www.myblog.task.data.mapper.blog.BlogArticleMapper;
import com.www.myblog.task.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>@Description redis业务处理实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/7 21:14 </p>
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {
    /** 博客文章需要保存到redis的字段名称 **/
    private String[] articleFields = {"blogId","userId","title","groupName","blogTag","content","browse","praise","collect","comment","createTime"};
    @Autowired
    private BlogArticleMapper blogArticleMapper;

    /**
     * <p>@Description 更新博客统计量数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:20 </p>
     * @return void
     */
    @Override
    public void updateBlogNum() {
        //获取所有博客的key
        Set<String> keySet = RedisOperation.getAllKeys(RedisKeyConstant.BLOG_ARTICLE + CharConstant.STAR);
        if(CollectionUtils.isNotEmpty(keySet)){
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (String key : keySet){
                //获取剩余有效时间
                long expireTime = RedisOperation.getExpireTime(key, TimeUnit.HOURS);
                //剩余时间小于一天，则需要更新数据到数据库
                if(expireTime <= 24){
                    //需要保存到redis的字段名称
                    BlogArticleDTO articleDTO = RedisOperation.hashGet(key,BlogArticleDTO.class,articleFields);
                    String lockKey = RedisKeyConstant.BLOG_ARTICLE_LOCK + articleDTO.getBlogId();//分布式锁key
                    String value = UUID.randomUUID().toString();
                    boolean isWait = true; //是否等待获取分布式锁
                    while (isWait){
                        try {
                            //添加分布式锁，实现同一时间只有一个现场操作
                            if(RedisOperation.lock(lockKey, value,10)){
                                isWait = false;
                                UpdateWrapper<BlogArticleEntity> wrapper = new UpdateWrapper<>();
                                wrapper.lambda().eq(BlogArticleEntity::getBlogId,articleDTO.getBlogId());
                                wrapper.lambda().set(BlogArticleEntity::getBrowse,articleDTO.getBrowse())
                                                .set(BlogArticleEntity::getPraise,articleDTO.getPraise())
                                                .set(BlogArticleEntity::getComment,articleDTO.getComment())
                                                .set(BlogArticleEntity::getCollect,articleDTO.getCollect())
                                                .set(BlogArticleEntity::getUpdateTime, DateUtils.getCurrentDateTime());
                                int count = blogArticleMapper.update(null,wrapper);
                                if(count <= 0){
                                    log.error("博客id：{}更新博客统计量数据失败",articleDTO.getBlogId());
                                }else {
                                    if(!RedisOperation.deleteKey(key)){
                                        log.error("博客id：{}更新博客统计量数据后删除redis数据失败",articleDTO.getBlogId());
                                    }
                                }
                            }
                        }catch (Exception e){
                            isWait = false;
                            log.error("更新博客统计量数据异常，异常信息：{}",e.getMessage());
                        }finally {
                            // 释放锁
                            RedisOperation.unlock(lockKey,value);
                        }
                    }
                }
            }
            stopWatch.stop();
            log.info("更新博客统计量数据耗时：{}秒", NumberUtils.format3(stopWatch.getTotalTimeSeconds()));
        }
    }
}
