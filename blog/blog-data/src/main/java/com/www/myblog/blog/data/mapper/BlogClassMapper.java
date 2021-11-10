package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.BlogClassEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客分类信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogClassMapper extends BaseMapper<BlogClassEntity> {
    /**
     * <p>@Description 根据主键删除数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:12 </p>
     * @param bcId 主键
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long bcId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:12 </p>
     * @param record 博客分类信息
     * @return int 插入条数
     */
    int insert(BlogClassEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:12 </p>
     * @param record 博客分类信息
     * @return int 插入条数
     */
    int insertNotNull(BlogClassEntity record);
    /**
     * <p>@Description 根据主键查询数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:13 </p>
     * @param bcId 主键
     * @return com.www.myblog.blog.data.entity.BlogClassEntity 博客分类信息
     */
    BlogClassEntity selectByPrimaryKey(Long bcId);
    /**
     * <p>@Description 根据主键更新数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:13 </p>
     * @param record 博客分类信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(BlogClassEntity record);
}