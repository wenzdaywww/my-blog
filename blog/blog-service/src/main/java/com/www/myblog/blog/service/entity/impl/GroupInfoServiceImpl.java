package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.GroupInfoEntity;
import com.www.myblog.blog.data.mapper.GroupInfoMapper;
import com.www.myblog.blog.service.entity.IGroupInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 分组信息表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:09 </p>
 */
@Service
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfoEntity> implements IGroupInfoService {
    @Autowired
    private GroupInfoMapper groupInfoMapper;

    /**
     * <p>@Description 创建分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:36 </p>
     * @param entity 分组信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(GroupInfoEntity entity) {
        if(entity == null){
            return false;
        }
        int count = groupInfoMapper.insert(entity);
        return count > 0;
    }

    /**
     * <p>@Description 根据主键查询分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:34 </p>
     * @param groupId 分组主键
     * @return com.www.myblog.blog.data.entity.GroupInfoEntity 分组信息
     */
    @Override
    public GroupInfoEntity findById(Long groupId) {
        if(groupId == null){
            return null;
        }
        return groupInfoMapper.selectById(groupId);
    }
}
