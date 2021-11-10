package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.BlogCommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客评论Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogCommentMapper extends BaseMapper<BlogCommentEntity> {
    /**
     * <p>@Description 根据主键删除数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:17 </p>
     * @param commentId 评论ID
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long commentId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:17 </p>
     * @param record 评论信息
     * @return int 插入条数
     */
    int insert(BlogCommentEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:17 </p>
     * @param record 评论信息
     * @return int 插入条数
     */
    int insertNotNull(BlogCommentEntity record);
    /**
     * <p>@Description 根据主键查询数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:17 </p>
     * @param commentId 评论ID
     * @return com.www.myblog.blog.data.entity.BlogCommentEntity 评论信息
     */
    BlogCommentEntity selectByPrimaryKey(Long commentId);
    /**
     * <p>@Description 根据主键更新数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:17 </p>
     * @param record 评论信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(BlogCommentEntity record);
}