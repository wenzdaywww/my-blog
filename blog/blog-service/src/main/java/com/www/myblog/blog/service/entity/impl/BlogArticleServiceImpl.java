package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.service.entity.IBlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 博客文章service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 12:43 </p>
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticleEntity> implements IBlogArticleService {
    @Autowired
    private BlogArticleMapper blogArticleMapper;

    /**
     * <p>@Description 更新博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    @Override
    public boolean updateEntity(UpdateWrapper<BlogArticleEntity> wrapper) {
        return this.updateEntity(null,wrapper);
    }
    /**
     * <p>@Description 更新博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param entity  更新内容
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    @Override
    public boolean updateEntity(BlogArticleEntity entity, UpdateWrapper<BlogArticleEntity> wrapper) {
        if(wrapper == null){
            return false;
        }
        int count = blogArticleMapper.update(entity,wrapper);
        return count > 0;
    }
    /**
     * <p>@Description 创建博客文章信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:51 </p>
     * @param entity 博客文章
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(BlogArticleEntity entity) {
        if(entity == null){
            return false;
        }
        int count = blogArticleMapper.insert(entity);
        return count > 0;
    }
    /**
     * <p>@Description 根据博客id查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:48 </p>
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.entity.BlogArticleEntity 博客信息
     */
    @Override
    public BlogArticleEntity findById(Long blogId) {
        if(blogId == null){
            return null;
        }
        return blogArticleMapper.selectById(blogId);
    }
}
