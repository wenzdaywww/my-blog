package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.TagInfoEntity;
import com.www.myblog.blog.data.mapper.TagInfoMapper;
import com.www.myblog.blog.service.entity.ITagInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description 标签信息表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:09 </p>
 */
@Service
public class TagInfoServiceImpl extends ServiceImpl<TagInfoMapper, TagInfoEntity> implements ITagInfoService {
    @Autowired
    private TagInfoMapper tagInfoMapper;

    /**
     * <p>@Description 根据标签id集合查询标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:39 </p>
     * @param tagIds 标签id集合
     * @return java.util.List<com.www.myblog.blog.data.entity.TagInfoEntity> 标签信息
     */
    @Override
    public List<TagInfoEntity> findByIds(List<Long> tagIds) {
        if(CollectionUtils.isEmpty(tagIds)){
            return null;
        }
        QueryWrapper<TagInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(TagInfoEntity::getTagId,tagIds);
        return tagInfoMapper.selectList(wrapper);
    }
}
