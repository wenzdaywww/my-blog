package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.ClassificationDTO;
import com.www.myblog.blog.data.entity.ClassificationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>@Description 博客分类类型Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface ClassificationMapper extends BaseMapper<ClassificationEntity> {
    /**
     * <p>@Description 查询所有博客分类 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:12 </p>
     * @return java.util.List<com.www.myblog.blog.data.dto.ClassificationDTO>
     */
    List<ClassificationDTO> findAllBlogClass();
}