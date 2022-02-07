package com.www.myblog.blog.service.redis;

import com.www.common.pojo.dto.security.ScopeDTO;
import com.www.common.pojo.dto.redis.BlogArticleDTO;

import java.util.List;

/**
 * <p>@Description redis的service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/3 23:12 </p>
 */
public interface IRedisService {
    /**
     * <p>@Description 更新博客统计量 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/5 11:59 </p>
     * @param name 更新的名称。如收藏量，点赞量，评论数，浏览量
     * @param blogId 博客id
     * @param isAdd 是否增加，true增加，false减少
     * @return boolean true更新成功，false失败
     */
    boolean updateBlogNum(String name,Long blogId, boolean isAdd);
    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    List<ScopeDTO> findUrlScope();
    /**
     * <p>@Description 从redis中获取博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 23:18 </p>
     * @param blogId 博客id
     * @return com.www.common.pojo.dto.redis.BlogArticleDTO 博客信息
     */
    BlogArticleDTO getArticleInfo(Long blogId);
    /**
     * <p>@Description 保存博客信息到redis中 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 23:18 </p>
     * @param articleDTO 博客id
     * @return true保存成功，false保存失败
     */
    boolean saveArticleInfo(BlogArticleDTO articleDTO);
}
