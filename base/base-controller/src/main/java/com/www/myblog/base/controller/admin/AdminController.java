package com.www.myblog.base.controller.admin;

import com.www.common.config.oauth2.constant.AuthorityContant;
import com.www.common.data.response.Result;
import com.www.myblog.base.data.dto.MonitorInfoDTO;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.dto.SysRoleDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.myblog.base.service.entity.IMonitorInfoService;
import com.www.myblog.base.service.menu.IMenuInfoService;
import com.www.myblog.base.service.user.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>@Description 菜单cpntroller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/11 16:23 </p>
 */
@RestController
@RequestMapping("admin")
@PreAuthorize(AuthorityContant.ADMIN)
public class AdminController {
    @Autowired
    private IMenuInfoService menuInfoService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IMonitorInfoService monitorInfoService;

    /**
     * <p>@Description 查询监控信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/7/2 14:21 </p>
     * @param name 监控名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return Response<List <MonitorInfoDTO>>
     */
    @GetMapping("monitor")
    public Result<List<MonitorInfoDTO>> findMonitorInfo(String name, int pageNum, long pageSize){
        return monitorInfoService.findMonitorInfo(name,pageNum,pageSize);
    }
    /**
     * <p>@Description 删除菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 23:57 </p>
     * @param menuId 菜单ID
     * @return Response<java.lang.String>
     */
    @PostMapping("down")
    public Result<String> deleteMenu(Long menuId){
        return menuInfoService.deleteMenu(menuId);
    }
    /**
     * <p>@Description 修改或创建菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 19:58 </p>
     * @param menu 菜单信息
     * @return Response<String>
     */
    @PostMapping("edit")
    public Result<String> updateOrSaveMenu(SysMenuDTO menu){
        return menuInfoService.updateOrSave(menu);
    }
    /**
     * <p>@Description 查询所有菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 16:58 </p>
     * @param module 菜单归属模块
     * @param menuType 菜单类型
     * @param roleCode 角色归属
     * @param menuCode 菜单编码
     * @param menuUrl 菜单路径
     * @param vuePath router的path路径
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return Response<List<SysMenuDTO>>
     */
    @GetMapping("menus")
    public Result<List<SysMenuDTO>> findAllMenu(String module,String menuType, String roleCode, String menuCode, String menuUrl,String vuePath, int pageNum, long pageSize){
        SysMenuDTO menuDTO = new SysMenuDTO();
        menuDTO.setModule(module);
        menuDTO.setMenuType(menuType);
        menuDTO.setRoleCode(roleCode);
        menuDTO.setMenuCode(menuCode);
        menuDTO.setMenuUrl(menuUrl);
        menuDTO.setVuePath(vuePath);
        return menuInfoService.findAllMenu(menuDTO,pageNum,pageSize);
    }
    /**
     * <p>@Description 查询所有用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/30 21:10 </p>
     * @param stateCd 用户状态
     * @param userId 用户id
     * @param userName 用户名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return Response<List<SysUserDTO>>
     */
    @GetMapping("users")
    public Result<List<SysUserDTO>> findAllUser(String stateCd, String userId, String userName, int pageNum, long pageSize){
        return userInfoService.findAllUser(stateCd,userId,userName,pageNum,pageSize);
    }
    /**
     * <p>@Description 更新用户状态 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 20:58 </p>
     * @param userId 用户id
     * @param stateCd 用户状态
     * @param expired 是否过期
     * @param locked 账号是否锁定
     * @param credentials 密码是否过期
     * @return Response<String>
     */
    @PostMapping("state")
    public Result<String> updateState(String userId,String stateCd,String expired,String locked,String credentials){
        return userInfoService.updateState(userId,stateCd,expired,locked,credentials);
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return Response<List<SysUserRoleDTO>>
     */
    @GetMapping("role")
    public Result<List<SysRoleDTO>> findAllRole(){
        return userInfoService.findAllRole();
    }
}
