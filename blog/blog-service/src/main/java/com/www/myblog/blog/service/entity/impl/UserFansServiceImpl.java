package com.www.myblog.blog.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.blog.data.entity.UserFansEntity;
import com.www.myblog.blog.data.mapper.UserFansMapper;
import com.www.myblog.blog.service.entity.IUserFansService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/29 15:45 </p>
 */
@Service
public class UserFansServiceImpl extends ServiceImpl<UserFansMapper, UserFansEntity> implements IUserFansService {
    @Autowired
    private UserFansMapper userFansMapper;

    /**
     * <p>@Description 根据主键删除用户关注信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:45 </p>
     * @param ufId 主键
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteEntity(Long ufId) {
        if(ufId == null){
            return false;
        }
        int count = userFansMapper.deleteById(ufId);
        return count > 0;
    }
    /**
     * <p>@Description 创建用户关注信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:43 </p>
     * @param entity 用户关注信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(UserFansEntity entity) {
        if(entity == null){
            return false;
        }
        int count = userFansMapper.insert(entity);
        return count > 0;
    }
    /**
     * <p>@Description 根据用户id和粉丝ID查询用户关注信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/29 15:47 </p>
     * @param userId 用户ID
     * @param fansId 粉丝ID
     * @return com.www.myblog.blog.data.entity.UserFansEntity
     */
    @Override
    public UserFansEntity findUserFansEntity(String userId, String fansId) {
        if(StringUtils.isAnyBlank(userId,fansId)){
            return null;
        }
        QueryWrapper<UserFansEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserFansEntity::getUserId,userId);
        wrapper.lambda().eq(UserFansEntity::getFansId,fansId);
        return userFansMapper.selectOne(wrapper);
    }
}
