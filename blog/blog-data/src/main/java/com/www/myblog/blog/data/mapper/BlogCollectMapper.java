package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.entity.BlogCollectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>@Description 博客收藏Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogCollectMapper extends BaseMapper<BlogCollectEntity> {
    /**
     * <p>@Description 查询用户的博客收藏列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 19:11 </p>
     * @param page 分页信息
     * @param userId 用户id
     * @param cgId 收藏分组id
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.www.myblog.blog.data.dto.BlogArticleDTO> 博客收藏列表
     */
    Page<BlogArticleDTO> findCollectList(Page<BlogArticleDTO> page, @Param("userId") String userId,@Param("cgId") Long cgId);
}