package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogTagEntity;
import com.www.myblog.blog.data.mapper.BlogTagMapper;
import com.www.myblog.blog.service.entity.IBlogTagService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.WeakHashMap;

/**
 * <p>@Description 博客标签表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:09 </p>
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTagEntity> implements IBlogTagService {
    @Autowired
    private BlogTagMapper blogTagMapper;


    /**
     * <p>@Description 批量创建博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:51 </p>
     * @param tagList 博客标签信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntityBatch(List<BlogTagEntity> tagList) {
        if(CollectionUtils.isEmpty(tagList)){
            return false;
        }
        return this.saveBatch(tagList);
    }

    /**
     * <p>@Description 根据博客标签id删除所有博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:34 </p>
     * @param btIds 博客标签id
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteById(List<Long> btIds) {
        if(CollectionUtils.isEmpty(btIds)){
            return false;
        }
        int count = blogTagMapper.deleteBatchIds(btIds);
        return count > 0;
    }
    /**
     * <p>@Description 根据博客id删除所有博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:34 </p>
     * @param blogId 博客id
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteByBlogId(Long blogId) {
        if(blogId == null){
            return false;
        }
        QueryWrapper<BlogTagEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BlogTagEntity::getBlogId,blogId);
        int count = blogTagMapper.delete(wrapper);
        return count > 0;
    }

    /**
     * <p>@Description 根据博客id查询标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:07 </p>
     * @param blogId 博客id
     * @return java.util.List<com.www.myblog.blog.data.entity.BlogTagEntity> 标签信息
     */
    @Override
    public List<BlogTagEntity> findEntityByBlogId(Long blogId) {
        if(blogId == null){
            return null;
        }
        QueryWrapper<BlogTagEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BlogTagEntity::getBlogId,blogId);
        return blogTagMapper.selectList(wrapper);
    }
    /**
     * <p>@Description 创建博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:31 </p>
     * @param entity 博客标签信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(BlogTagEntity entity) {
        if(entity == null){
            return false;
        }
        int count = blogTagMapper.insert(entity);
        return count > 0;
    }
}
