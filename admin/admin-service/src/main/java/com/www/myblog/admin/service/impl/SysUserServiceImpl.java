package com.www.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.data.mapper.SysUserMapper;
import com.www.myblog.admin.service.ISysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 用户表service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 15:32 </p>
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * <p>@Description 根据用户ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 15:32 </p>
     * @param userId 用户ID
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    @Override
    public SysUserEntity findUserById(String userId) {
        if(StringUtils.isBlank(userId)){
            return null;
        }
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserEntity::getUserId,userId);
        return sysUserMapper.selectOne(wrapper);
    }
}
