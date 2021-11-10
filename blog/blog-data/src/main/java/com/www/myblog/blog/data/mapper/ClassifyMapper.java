package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.ClassifyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客分类类型Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface ClassifyMapper extends BaseMapper {
    /**
     * <p>@Description 根据主键删除数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:22 </p>
     * @param classId 分类ID
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long classId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:22 </p>
     * @param record 分类信息
     * @return int 插入条数
     */
    int insert(ClassifyEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:22 </p>
     * @param record 分类信息
     * @return int 插入条数
     */
    int insertNotNull(ClassifyEntity record);
    /**
     * <p>@Description 根据主键查询数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:22 </p>
     * @param classId 分类ID
     * @return com.www.myblog.blog.data.entity.ClassifyEntity 分类信息
     */
    ClassifyEntity selectByPrimaryKey(Long classId);
    /**
     * <p>@Description 根据分类ID更新分类信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:22 </p>
     * @param record 分类信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(ClassifyEntity record);
}