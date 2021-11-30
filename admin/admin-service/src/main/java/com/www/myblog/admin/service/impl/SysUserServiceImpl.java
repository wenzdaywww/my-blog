package com.www.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.data.mapper.SysUserMapper;
import com.www.myblog.admin.service.ISysUserService;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/30 21:10 </p>
     * @param stateCd 用户状态
     * @param userId 用户id
     * @param userName 用户名称
     * @param pageNum  当前页数
     * @param pageSize 页面条数
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserDTO>>
     */
    @Override
    public ResponseDTO<List<SysUserEntity>> findAllUser(String stateCd, String userId,String userName, int pageNum, int pageSize) {
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(stateCd)){
            wrapper.lambda().eq(SysUserEntity::getStateCd,stateCd);
        }
        if(StringUtils.isNotBlank(userId)){
            wrapper.lambda().eq(SysUserEntity::getUserId,userId);
        }
        if(StringUtils.isNotBlank(userName)){
            wrapper.lambda().eq(SysUserEntity::getUserName,userName);
        }
        Page<SysUserEntity> page = new Page<>(pageNum,pageSize);
        page = sysUserMapper.selectPage(page,wrapper);
        List<SysUserEntity> userList =  page.getRecords();
        ResponseDTO<List<SysUserEntity>> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,userList);
        responseDTO.setPageNum(pageNum);
        responseDTO.setPageSize(pageSize);
        responseDTO.setTotalNum(page.getTotal());
        return responseDTO;
    }

    /**
     * <p>@Description 查询用户拥有的角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:45 </p>
     * @param userId 用户ID
     * @return java.util.List<com.www.myblog.admin.data.entity.SysRoleEntity> 角色信息
     */
    @Override
    public List<SysRoleEntity> findUserRole(String userId) {
        if(StringUtils.isBlank(userId)){
            return null;
        }
        return sysUserMapper.findUserRole(userId);
    }

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
