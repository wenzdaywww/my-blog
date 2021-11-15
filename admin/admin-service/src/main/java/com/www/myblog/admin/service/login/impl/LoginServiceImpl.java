package com.www.myblog.admin.service.login.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.data.mapper.SysUserMapper;
import com.www.myblog.admin.service.login.ILoginService;
import com.www.myblog.common.pojo.ResponseDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 用户登录service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 14:35 </p>
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * <p>@Description 用户登入 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 14:33 </p>
     * @param userId   用户ID
     * @param password 密码
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    @Override
    public ResponseDTO<SysUserEntity> userLogin(String userId, String password) {
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserEntity::getUserId,userId);
        SysUserEntity userEntity = sysUserMapper.selectOne(wrapper);
        ResponseDTO<SysUserEntity> responseDTO = new ResponseDTO<>(null);
        if(userEntity != null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(passwordEncoder.matches(password,userEntity.getPassWord())){
                responseDTO.setData(userEntity);
            } ;
        }
        return responseDTO;
    }
}
