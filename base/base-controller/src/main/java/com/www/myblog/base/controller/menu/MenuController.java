package com.www.myblog.base.controller.menu;

import com.www.common.pojo.constant.AuthorityContant;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.service.menu.IMenuInfoService;
import com.www.common.pojo.dto.response.ResponseDTO;
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
@RequestMapping("menu")
@PreAuthorize(AuthorityContant.ADMIN)
public class MenuController {
    @Autowired
    private IMenuInfoService menuInfoService;

    /**
     * <p>@Description 删除菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 23:57 </p>
     * @param menuId 菜单ID
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("down")
    public ResponseDTO<String> deleteMenu(Long menuId){
        return menuInfoService.deleteMenu(menuId);
    }
    /**
     * <p>@Description 修改或创建菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 19:58 </p>
     * @param menu 菜单信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("edit")
    public ResponseDTO<String> updateOrSaveMenu(SysMenuDTO menu){
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysMenuDTO>>
     */
    @GetMapping("all")
    public ResponseDTO<List<SysMenuDTO>> findAllMenu(String module,String menuType, String roleCode, String menuCode, String menuUrl,String vuePath, int pageNum, int pageSize){
        SysMenuDTO menuDTO = new SysMenuDTO();
        menuDTO.setModule(module);
        menuDTO.setMenuType(menuType);
        menuDTO.setRoleCode(roleCode);
        menuDTO.setMenuCode(menuCode);
        menuDTO.setMenuUrl(menuUrl);
        menuDTO.setVuePath(vuePath);
        return menuInfoService.findAllMenu(menuDTO,pageNum,pageSize);
    }
}
