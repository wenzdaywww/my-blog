package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.base.data.entity.SysUserEntity;
import com.www.myblog.base.data.mapper.SysUserMapper;
import com.www.myblog.base.service.entity.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description SYS_USER表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:35 </p>
     * @param entity 用户信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(SysUserEntity entity) {
        if(entity == null){
            return false;
        }
        int count = sysUserMapper.insert(entity);
        return count > 0;
    }

    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    @Override
    public boolean updateEntity(UpdateWrapper<SysUserEntity> wrapper) {
        return this.updateEntity(null,wrapper);
    }
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param entity  更新内容
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    @Override
    public boolean updateEntity(SysUserEntity entity, UpdateWrapper<SysUserEntity> wrapper) {
        if(wrapper == null){
            return false;
        }
        int count = sysUserMapper.update(entity,wrapper);
        return count > 0;
    }
    /**
     * <p>@Description 根据用户ID集合查询用户信息条数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:24 </p>
     * @param userList 用户id集合
     * @return int 用户信息条数
     */
    @Override
    public int findUserCountById(List<String> userList) {
        if(CollectionUtils.isEmpty(userList)){
            return 0;
        }
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysUserEntity::getUserId,userList);
        return sysUserMapper.selectCount(wrapper);
    }
    /**
     * <p>@Description 根据用户ID集合查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:21 </p>
     * @param userList 用户id集合
     * @return java.util.List<com.www.myblog.base.data.entity.SysUserEntity> 用户信息集合
     */
    @Override
    public List<SysUserEntity> findUserEntityById(List<String> userList) {
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysUserEntity::getUserId,userList);
        return sysUserMapper.selectList(wrapper);
    }
    /**
     * <p>@Description 根据用户ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param userId 用户ID
     * @return com.www.myblog.base.data.entity.SysUserEntity 用户信息
     */
    @Override
    public SysUserEntity findUserEntityById(String userId) {
        if(StringUtils.isBlank(userId)){
            return null;
        }
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserEntity::getUserId,userId);
        return sysUserMapper.selectOne(wrapper);
    }
}
