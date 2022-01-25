package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.data.entity.TagInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>@Description 博客分类类型Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface TagInfoMapper extends BaseMapper<TagInfoEntity> {
    /**
     * <p>@Description 查询所有博客标签 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:12 </p>
     * @return java.util.List<com.www.myblog.blog.data.dto.ClassificationDTO>
     */
    List<TagInfoDTO> findAllBlogTag();
}