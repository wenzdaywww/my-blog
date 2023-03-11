package com.www.uaa.config.authorize.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.www.common.config.code.CodeDict;
import com.www.common.pojo.dto.security.UserDetailDTO;
import com.www.common.pojo.enums.CodeKeyEnum;
import com.www.uaa.data.entity.SysUserEntity;
import com.www.uaa.data.enums.CodeTypeEnum;
import com.www.uaa.data.mapper.SysRoleMapper;
import com.www.uaa.data.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description 用户详细信息服务类，用于实现spring security的登录认证 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:12 </p>
 */
@Slf4j
@Component
public class UserServiceHandler implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * <p>@Description 加载用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:12 </p>
     * @param userId 用户ID
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("验证{}用户信息",userId);
        if(StringUtils.isBlank(userId)){
            return null;
        }
        QueryWrapper<SysUserEntity> userWrapper = new QueryWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userId);
        SysUserEntity userEntity = sysUserMapper.selectOne(userWrapper);
        if(userEntity == null){
            return null;
        }
        UserDetailDTO userDTO = new UserDetailDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setEnabled(StringUtils.equals(userEntity.getStateCd(), CodeDict.getValue(CodeTypeEnum.USER_STATUS.getCodeType(), CodeKeyEnum.K1.getKey())));
        userDTO.setAccountNonExpired(StringUtils.equals(userEntity.getNotExpired(), CodeDict.getValue(CodeTypeEnum.YES_NO.getCodeType(), CodeKeyEnum.K1.getKey())));
        userDTO.setCredentialsNonExpired(StringUtils.equals(userEntity.getCredentialsNotExpired(), CodeDict.getValue(CodeTypeEnum.YES_NO.getCodeType(), CodeKeyEnum.K1.getKey())));
        userDTO.setAccountNonLocked(StringUtils.equals(userEntity.getNotLocked(), CodeDict.getValue(CodeTypeEnum.YES_NO.getCodeType(), CodeKeyEnum.K1.getKey())));
        //查询用户的角色
        List<String> roleList = sysRoleMapper.findUserRole(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(roleList)){
            for (String roleCode : roleList){
                authorities.add(new SimpleGrantedAuthority(roleCode));
            }
        }
        //密码必须加密，否则无效
        User user = new User(userDTO.getUserId(), userDTO.getPassword(), userDTO.isEnabled(),
                userDTO.isAccountNonExpired(),userDTO.isCredentialsNonExpired(), userDTO.isAccountNonLocked(),authorities);
        return user;
    }
}
