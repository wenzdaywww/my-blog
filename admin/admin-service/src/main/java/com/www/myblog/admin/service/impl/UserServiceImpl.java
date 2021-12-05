package com.www.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.admin.data.dto.SysRoleDTO;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.data.enums.CommonEnum;
import com.www.myblog.admin.data.mapper.SysRoleMapper;
import com.www.myblog.admin.data.mapper.SysUserMapper;
import com.www.myblog.admin.service.IUserService;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description 用户信息service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 15:32 </p>
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserRoleDTO>>
     */
    @Override
    public ResponseDTO<List<SysRoleDTO>> findAllRole() {
        List<SysRoleDTO> list = sysRoleMapper.findAllRole();
        return new ResponseDTO<>(ResponseEnum.SUCCESS,list);
    }

    /**
     * <p>@Description 更新用户状态 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 20:58 </p>
     * @param userId      用户id
     * @param stateCd     用户状态
     * @param notExpired  是否过期
     * @param notLocked   账号是否锁定
     * @param credentialsNotExpired 密码是否过期
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> updateState(String userId, String stateCd, String notExpired, String notLocked, String credentialsNotExpired) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(!StringUtils.containsAny(stateCd, CommonEnum.STATE_CD_1.getCode(), CommonEnum.STATE_CD_2.getCode(), CommonEnum.STATE_CD_3.getCode())
                || !StringUtils.containsAny(notExpired, CommonEnum.YES_1.getCode(), CommonEnum.NO_0.getCode())
                || !StringUtils.containsAny(notLocked, CommonEnum.YES_1.getCode(), CommonEnum.NO_0.getCode())
                || !StringUtils.containsAny(credentialsNotExpired, CommonEnum.YES_1.getCode(), CommonEnum.NO_0.getCode())){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户信息失败");
            return responseDTO;
        }
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userId);
        userWrapper.lambda().set(SysUserEntity::getStateCd,stateCd);
        userWrapper.lambda().set(SysUserEntity::getNotExpired,notExpired);
        userWrapper.lambda().set(SysUserEntity::getNotLocked,notLocked);
        userWrapper.lambda().set(SysUserEntity::getCredentialsNotExpired,credentialsNotExpired);
        int count = sysUserMapper.update(null,userWrapper);
        if(count != 0){
            responseDTO.setResponseCode(ResponseEnum.SUCCESS,"更新用户信息成功");
        }else {
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户信息失败");
        }
        return responseDTO;
    }

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
