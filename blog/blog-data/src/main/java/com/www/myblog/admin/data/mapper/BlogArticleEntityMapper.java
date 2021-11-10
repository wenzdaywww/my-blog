package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.entity.BlogArticleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:04 </p>
 */
@Mapper
public interface BlogArticleEntityMapper extends BaseMapper {
    /**
     * delete by primary key
     * @param blogId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long blogId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(BlogArticleEntity record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(BlogArticleEntity record);

    /**
     * select by primary key
     * @param blogId primary key
     * @return object by primary key
     */
    BlogArticleEntity selectByPrimaryKey(Long blogId);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(BlogArticleEntity record);
}