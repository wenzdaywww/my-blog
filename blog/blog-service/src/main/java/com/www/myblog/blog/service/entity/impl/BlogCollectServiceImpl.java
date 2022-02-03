package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.BlogCollectEntity;
import com.www.myblog.blog.data.mapper.BlogCollectMapper;
import com.www.myblog.blog.service.entity.IBlogCollectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 博客收藏表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 10:52 </p>
 */
@Service
public class BlogCollectServiceImpl extends ServiceImpl<BlogCollectMapper, BlogCollectEntity> implements IBlogCollectService {
    @Autowired
    private BlogCollectMapper blogCollectMapper;


    /**
     * <p>@Description 更新博客收藏表信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 19:52 </p>
     * @param wrapper 更新条件及内容
     * @return boolean true更新成功，false更新失败
     */
    @Override
    public boolean updateEntity(UpdateWrapper<BlogCollectEntity> wrapper) {
        if(wrapper == null){
            return false;
        }
        int count =blogCollectMapper.update(null,wrapper);
        return count > 0;
    }
    /**
     * <p>@Description 根据博客id查询该博客被收藏的次数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:58 </p>
     * @param blogId 博客id
     * @return int 博客被收藏的次数
     */
    @Override
    public int findBlogCollectCount(Long blogId) {
        if(blogId == null){
            return 0;
        }
        QueryWrapper<BlogCollectEntity> colWrapper = new QueryWrapper<>();
        colWrapper.lambda().eq(BlogCollectEntity::getBlogId,blogId);
        return blogCollectMapper.selectCount(colWrapper);
    }
    /**
     * <p>@Description 判断该用户是否已收藏该博客 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:16 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return boolean true已收藏，false未收藏
     */
    @Override
    public boolean hasCollectBlog(String userId, Long blogId) {
        BlogCollectEntity collectEntity = this.findBlogCollectEntity(userId,blogId);
        return collectEntity != null;
    }
    /**
     * <p>@Description 新增博客收藏信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:05 </p>
     * @param collectEntity 博客收藏信息
     * @return com.www.myblog.blog.data.entity.BlogCollectEntity
     */
    @Override
    public boolean createBlogCollectEntity(BlogCollectEntity collectEntity) {
        if(collectEntity == null){
            return false;
        }
        int count = blogCollectMapper.insert(collectEntity);
        return count > 0;
    }
    /**
     * <p>@Description 删除博客收藏 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:00 </p>
     * @param collectId 博客收藏id
     * @return boolean true删除成功，false失败
     */
    @Override
    public boolean deleteBlogCollectEntity(Long collectId) {
        if(collectId == null){
            return false;
        }
        int count = blogCollectMapper.deleteById(collectId);
        return count > 0;
    }

    /**
     * <p>@Description 查询博客收藏信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:53 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.entity.BlogCollectEntity
     */
    @Override
    public BlogCollectEntity findBlogCollectEntity(String userId, Long blogId) {
        if(StringUtils.isBlank(userId) || blogId == null){
            return null;
        }
        QueryWrapper<BlogCollectEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BlogCollectEntity::getUserId,userId);
        wrapper.lambda().eq(BlogCollectEntity::getBlogId,blogId);
        return blogCollectMapper.selectOne(wrapper);
    }
}
