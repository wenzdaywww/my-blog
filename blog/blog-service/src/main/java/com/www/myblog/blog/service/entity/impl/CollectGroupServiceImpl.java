package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.CollectGroupEntity;
import com.www.myblog.blog.data.mapper.CollectGroupMapper;
import com.www.myblog.blog.service.entity.ICollectGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 收藏分组信息表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:09 </p>
 */
@Service
public class CollectGroupServiceImpl extends ServiceImpl<CollectGroupMapper, CollectGroupEntity> implements ICollectGroupService {
    @Autowired
    private CollectGroupMapper collectGroupMapper;


    /**
     * <p>@Description  新增收藏分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:33 </p>
     * @param entity 收藏分组信息
     * @return boolean true新增成功，false新增失败
     */
    @Override
    public boolean createEntity(CollectGroupEntity entity) {
        if(entity == null){
            return false;
        }
        int count = collectGroupMapper.insert(entity);
        return count > 0;
    }

    /**
     * <p>@Description 根据主键查询收藏分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:23 </p>
     * @param cgId 收藏分组id
     * @return com.www.myblog.blog.data.entity.CollectGroupEntity 收藏分组信息
     */
    @Override
    public CollectGroupEntity findById(Long cgId) {
        if(cgId == null){
            return null;
        }
        return collectGroupMapper.selectById(cgId);
    }
}
