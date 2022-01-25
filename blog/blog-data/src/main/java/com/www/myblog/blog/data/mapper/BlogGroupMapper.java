package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.entity.BlogGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 博客分组信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogGroupMapper extends BaseMapper<BlogGroupEntity> {
    /**
     * <p>@Description 获取博主博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @return java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>
     */
    List<BlogGroupDTO> findAuthorBlogGroup(@Param("userId") String userId);
}