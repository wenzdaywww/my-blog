package com.www.myblog.admin.service.security.impl;

import com.www.myblog.admin.data.constants.RedisKeyConstant;
import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.data.enums.CommonEnum;
import com.www.myblog.admin.data.mapper.SysMenuMapper;
import com.www.myblog.admin.data.mapper.SysUserMapper;
import com.www.myblog.admin.service.entity.ISysUserService;
import com.www.myblog.common.config.security.ISecurityServie;
import com.www.myblog.common.pojo.AuthorityDTO;
import com.www.myblog.common.pojo.UserDetailDTO;
import com.www.myblog.common.utils.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>@Description security的信息查询接口实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/12 16:07 </p>
 */
@Service
public class AdminSecurityServiceImpl implements ISecurityServie {
    private static Logger LOG = LoggerFactory.getLogger(AdminSecurityServiceImpl.class);
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * <p>@Description 根据用户ID查询用户的登录信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 15:46 </p>
     *
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.UserDetailDTO 用户的登录信息
     */
    @Override
    public UserDetailDTO findUserDetailById(String userId) {
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity != null){
            UserDetailDTO userDTO = new UserDetailDTO();
            userDTO.setUserId(userEntity.getUserId());
            userDTO.setPassWord(userEntity.getPassWord());
            userDTO.setEnabled(StringUtils.equals(userEntity.getStateCd(), CommonEnum.STATE_CD_1.getCode()));
            userDTO.setAccountNonExpired(StringUtils.equals(userEntity.getNotExpired(), CommonEnum.YES_1.getCode()));
            userDTO.setCredentialsNonExpired(StringUtils.equals(userEntity.getCredentialsNotExpired(), CommonEnum.YES_1.getCode()));
            userDTO.setAccountNonLocked(StringUtils.equals(userEntity.getNotLocked(), CommonEnum.YES_1.getCode()));
            return userDTO;
        }
        return null;
    }

    /**
     * <p>@Description 查询用户拥有的角色 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 15:52 </p>
     * @param userId 用户ID
     * @return java.util.List<java.lang.String> 角色集合
     */
    @Override
    public List<String> findUserRole(String userId) {
        if(StringUtils.isBlank(userId)){
            return null;
        }
        List<SysRoleEntity> entityList = sysUserMapper.findUserRole(userId);
        return CollectionUtils.isNotEmpty(entityList) ? entityList.stream().map(SysRoleEntity::getRoleName).collect(Collectors.toList()) : null;
    }

    /**
     * <p>@Description 查所询有请求权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 15:59 </p>
     * @return java.util.List<com.www.myblog.common.pojo.AuthorityDTO> 所有权限
     */
    @Override
    public List<AuthorityDTO> findAllAuthority() {
        boolean isWait = true; //是否等待获取分布式锁
        List<AuthorityDTO> authList = null;
        String value = UUID.randomUUID().toString();
        while (isWait){
            try {
                if(RedisUtils.lock(RedisKeyConstant.AUTHORITY_MENU_LOCK, value)){
                    isWait = false;
                    //从redis中获取所有请求权限
                    authList = (List<AuthorityDTO>)RedisUtils.listGet(RedisKeyConstant.AUTHORITY_MENU);
                    if(CollectionUtils.isNotEmpty(authList)){
                        return authList;
                    }else {
                        List<SysRoleMenuDTO> menuList = sysMenuMapper.findAllSecurityMenu();
                        if(CollectionUtils.isEmpty(menuList)){
                            return null;
                        }
                        authList = menuList.stream().map(
                                item-> {
                                    AuthorityDTO authDTO = new AuthorityDTO();
                                    authDTO.setUrl(item.getMenuUrl());
                                    authDTO.setRole(item.getRoleName());
                                    //将所有请求权限保存到redis中
                                    RedisUtils.listSet(RedisKeyConstant.AUTHORITY_MENU,authDTO);
                                    return authDTO;
                                }
                        ).collect(Collectors.toList());
                    }
                }
            }catch (Exception e){
                LOG.info("查所询有请求权限，发生异常：{}",e.getMessage());
            }finally {
                // 释放锁
                RedisUtils.unlock(RedisKeyConstant.AUTHORITY_MENU_LOCK,value);
            }
        }
        return authList;
    }
}
