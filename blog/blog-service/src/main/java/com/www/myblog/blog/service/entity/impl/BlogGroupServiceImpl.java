package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogGroupEntity;
import com.www.myblog.blog.data.mapper.BlogGroupMapper;
import com.www.myblog.blog.service.entity.IBlogGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 博客分组表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:09 </p>
 */
@Service
public class BlogGroupServiceImpl extends ServiceImpl<BlogGroupMapper, BlogGroupEntity> implements IBlogGroupService {
    @Autowired
    private BlogGroupMapper blogGroupMapper;

    /**
     * <p>@Description 创建博客分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:27 </p>
     * @param entity 博客分组信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(BlogGroupEntity entity) {
        if(entity == null){
            return false;
        }
        int count = blogGroupMapper.insert(entity);
        return count > 0;
    }
}
