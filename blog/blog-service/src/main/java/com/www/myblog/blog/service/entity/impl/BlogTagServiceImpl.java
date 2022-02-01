package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogTagEntity;
import com.www.myblog.blog.data.mapper.BlogTagMapper;
import com.www.myblog.blog.service.entity.IBlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
