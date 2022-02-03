package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogPraiseEntity;
import com.www.myblog.blog.data.mapper.BlogPraiseMapper;
import com.www.myblog.blog.service.entity.IBlogPraiseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 博客点赞表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:09 </p>
 */
@Service
public class BlogPraiseServiceImpl extends ServiceImpl<BlogPraiseMapper, BlogPraiseEntity> implements IBlogPraiseService {
    @Autowired
    private BlogPraiseMapper blogPraiseMapper;


    /**
     * <p>@Description 是否已经点赞该博客 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:39 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return boolean true已赞，false未赞
     */
    @Override
    public boolean hasPraise(String userId, Long blogId) {
        BlogPraiseEntity entity = this.findEntity(userId,blogId);
        return entity != null;
    }
    /**
     * <p>@Description 查询用户对博客的点赞信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:28 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.entity.BlogPaiseEntity
     */
    @Override
    public BlogPraiseEntity findEntity(String userId, Long blogId) {
        if(StringUtils.isBlank(userId) || blogId== null){
            return null;
        }
        QueryWrapper<BlogPraiseEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BlogPraiseEntity::getBlogId,blogId);
        wrapper.lambda().eq(BlogPraiseEntity::getUserId,userId);
        return blogPraiseMapper.selectOne(wrapper);
    }
    /**
     * <p>@Description 创建博客点赞信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:28 </p>
     * @param entity 博客点赞信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(BlogPraiseEntity entity) {
        if(entity == null){
            return false;
        }
        int count = blogPraiseMapper.insert(entity);
        return count > 0;
    }
    /**
     * <p>@Description 删除博客点赞信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:28 </p>
     * @param bpId 博客点赞主键
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteEntity(Long bpId) {
        if(bpId == null){
            return false;
        }
        int count = blogPraiseMapper.deleteById(bpId);
        return count > 0;
    }
}
