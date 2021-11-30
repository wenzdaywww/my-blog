package com.www.myblog.admin.controller.user;

import com.www.myblog.admin.data.dto.SysUserDTO;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.service.ISysUserService;
import com.www.myblog.common.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>@Description 用户信息controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/30 20:54 </p>
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private ISysUserService sysUserService;

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/30 21:10 </p>
     * @param stateCd 用户状态
     * @param userId 用户id
     * @param userName 用户名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserDTO>>
     */
    @GetMapping("/all")
    public ResponseDTO<List<SysUserEntity>> findAllUser(String stateCd, String userId, String userName, int pageNum, int pageSize){
        return sysUserService.findAllUser(stateCd,userId,userName,pageNum,pageSize);
    }
}
