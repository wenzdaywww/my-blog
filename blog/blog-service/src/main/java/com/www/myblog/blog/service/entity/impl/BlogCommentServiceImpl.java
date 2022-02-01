package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogCommentEntity;
import com.www.myblog.blog.data.mapper.BlogCommentMapper;
import com.www.myblog.blog.service.entity.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:02 </p>
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogCommentEntity> implements IBlogCommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    /**
     * <p>@Description 根据主键查询博客评论信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:06 </p>
     * @param commentId 博客评论id
     * @return com.www.myblog.blog.data.entity.BlogCommentEntity 博客评论信息
     */
    @Override
    public BlogCommentEntity findById(Long commentId) {
        if(commentId == null){
            return null;
        }
        return blogCommentMapper.selectById(commentId);
    }

    /**
     * <p>@Description 创建博客评论信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:03 </p>
     * @param entity 博客评论信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(BlogCommentEntity entity) {
        if(entity == null){
            return false;
        }
        int count = blogCommentMapper.insert(entity);
        return count > 0;
    }
}
