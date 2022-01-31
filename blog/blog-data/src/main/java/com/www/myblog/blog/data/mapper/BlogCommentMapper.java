package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.entity.BlogCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>@Description 博客评论Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogCommentMapper extends BaseMapper<BlogCommentEntity> {
    /**
     * <p>@Description 查询评论列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 22:43 </p>
     * @param blogId 博客id
     * @param parentComId 父评论id
     * @return java.util.List<com.www.myblog.blog.data.dto.CommentDTO>
     */
    Page<CommentDTO> findCommentList(Page<CommentDTO> page, @Param("blogId") Long blogId, @Param("parentComId")Long parentComId);
}