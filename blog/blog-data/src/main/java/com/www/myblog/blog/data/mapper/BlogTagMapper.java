package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.data.entity.BlogTagEntity;
import com.www.myblog.common.dto.BlogTagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 博客标签信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTagEntity> {
    /**
     * <p>@Description 根据博客ID查询博客标签 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/25 21:42 </p>
     * @param blogId 博客ID
     * @return java.util.List<com.www.myblog.blog.data.dto.BlogClassDTO>
     */
    List<BlogTagDTO> findBlogTag(@Param("blogId") Long blogId);
    /**
     * <p>@Description 获取博主博客标签列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @return java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>
     */
    List<TagInfoDTO> findAuthorBlogTag(@Param("userId") String userId);
}