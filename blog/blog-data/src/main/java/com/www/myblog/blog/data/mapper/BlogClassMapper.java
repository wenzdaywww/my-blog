package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.ClassificationDTO;
import com.www.myblog.blog.data.entity.BlogClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 博客分类信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogClassMapper extends BaseMapper<BlogClassEntity> {
    /**
     * <p>@Description 获取博主博客分类列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @return java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>
     */
    List<ClassificationDTO> findAuthorBlogClass(@Param("userId") String userId);
}