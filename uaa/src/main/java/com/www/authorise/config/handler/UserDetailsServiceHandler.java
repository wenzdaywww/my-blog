package com.www.authorise.config.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.www.authorise.data.entity.SysUserEntity;
import com.www.authorise.data.enums.CommonEnum;
import com.www.authorise.data.mapper.SysRoleMapper;
import com.www.authorise.data.mapper.SysUserMapper;
import com.www.myblog.common.pojo.UserDetailDTO;
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
public class UserDetailsServiceHandler implements UserDetailsService {
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
        log.info("=====> 加载{}用户信息",userId);
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
        userDTO.setPassWord(userEntity.getPassWord());
        userDTO.setEnabled(StringUtils.equals(userEntity.getStateCd(), CommonEnum.STATE_CD_1.getCode()));
        userDTO.setAccountNonExpired(StringUtils.equals(userEntity.getNotExpired(), CommonEnum.YES_1.getCode()));
        userDTO.setCredentialsNonExpired(StringUtils.equals(userEntity.getCredentialsNotExpired(), CommonEnum.YES_1.getCode()));
        userDTO.setAccountNonLocked(StringUtils.equals(userEntity.getNotLocked(), CommonEnum.YES_1.getCode()));
        //查询用户的角色
        List<String> roleList = sysRoleMapper.findUserRole(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(roleList)){
            for (String roleName : roleList){
                authorities.add(new SimpleGrantedAuthority(roleName));
            }
        }
        //密码必须加密，否则无效
        User user = new User(userDTO.getUserId(), userDTO.getPassWord(), userDTO.isEnabled(),
                userDTO.isAccountNonExpired(),userDTO.isCredentialsNonExpired(), userDTO.isAccountNonLocked(),authorities);
        return user;
    }
}
