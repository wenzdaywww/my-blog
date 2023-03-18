package com.www.myblog.base.service.menu.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.config.code.CodeDict;
import com.www.common.config.code.enums.CodeKeyEnum;
import com.www.common.data.enums.ResponseEnum;
import com.www.common.data.response.Response;
import com.www.common.utils.DateUtils;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.entity.SysMenuEntity;
import com.www.myblog.base.data.entity.SysRoleEntity;
import com.www.myblog.base.data.entity.SysRoleMenuEntity;
import com.www.myblog.base.data.enums.CodeTypeEnum;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.service.entity.ISysMenuService;
import com.www.myblog.base.service.entity.ISysRoleMenuService;
import com.www.myblog.base.service.entity.ISysRoleService;
import com.www.myblog.base.service.menu.IMenuInfoService;
import com.www.myblog.base.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>@Description 菜单service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 20:42 </p>
 */
@Service
@Slf4j
public class MenuInfoServiceImpl implements IMenuInfoService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ISysMenuService sysMenuService;


    /**
     * <p>@Description 删除菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 23:57 </p>
     * @param menuId 菜单ID
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> deleteMenu(Long menuId) {
        SysMenuEntity menuEntity = sysMenuService.findById(menuId);
        if(menuEntity != null){
            return new Response<>(ResponseEnum.FAIL,"删除菜单失败，菜单不存在");
        }
        //根据菜单id删除菜单信息
        sysMenuService.deleteById(menuId);
        //根据菜单id删除角色菜单信息
        boolean isOk = sysRoleMenuService.deleteByMenuId(menuId);
        if(isOk){
            if(StringUtils.equals(menuEntity.getMenuType(),CodeDict.getValue(CodeTypeEnum.MENU_TYPE.getCodeType(), CodeKeyEnum.K2.getKey()))){
                redisService.updateRedisUrlScope(menuEntity.getModule());
            }
            return new Response<>(ResponseEnum.SUCCESS,"删除菜单成功");
        }else {
            return new Response<>(ResponseEnum.FAIL,"删除菜单失败");
        }
    }
    /**
     * <p>@Description 修改或创建菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 19:58 </p>
     * @param menu 菜单信息
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> updateOrSave(SysMenuDTO menu) {
        Response<String> response = new Response<>();
        if(menu == null || StringUtils.isAnyBlank(menu.getMenuCode(),menu.getMenuUrl(),menu.getMenuType())
            || CodeDict.isIllegalValue(CodeTypeEnum.MENU_TYPE.getCodeType(),menu.getMenuType())
        ){
            response.setResponse(ResponseEnum.FAIL,"更新菜单失败，信息有误");
            return response;
        }
        SysMenuEntity parentEntity = null;
        SysMenuEntity menuEntity = null;
        List<SysRoleEntity> roleList = null;
        // 查询菜单是否存在
        if(menu.getMenuId() != null){
            menuEntity = sysMenuService.findById(menu.getMenuId());
        }
        boolean isUpdate = menuEntity != null; //是否是更新
        // 查询父级菜单是否存在
        if(menu.getParentId() != null){
            parentEntity = sysMenuService.findById(menu.getParentId());
            if(parentEntity == null){
                response.setResponse(ResponseEnum.FAIL,"更新菜单失败，父级菜单不存在");
                return response;
            }
        }
        if(StringUtils.isNotBlank(menu.getRoleCode())){
            String[] roleArr = menu.getRoleCode().split(",");
            roleList = sysRoleService.findRoleEntityByName(roleArr);
            if(CollectionUtils.isEmpty(roleList)){
                response.setResponse(ResponseEnum.FAIL,"更新菜单失败，角色不存在");
                return response;
            }
        }
        // 更新菜单信息
        menuEntity = isUpdate ? menuEntity : new SysMenuEntity();
        menuEntity.setMenuCode(menu.getMenuCode());
        menuEntity.setMenuUrl(menu.getMenuUrl());
        menuEntity.setMenuName(menu.getMenuName());
        menuEntity.setVuePath(menu.getVuePath());
        menuEntity.setParentId(menu.getParentId());
        menuEntity.setMenuIcon(menu.getMenuIcon());
        menuEntity.setMenuOrder(menu.getMenuOrder());
        menuEntity.setMenuType(menu.getMenuType());
        menuEntity.setModule(menu.getModule());
        menuEntity.setIsDelete(menu.getIsDelete());
        menuEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        if(isUpdate){
            sysMenuService.updateEntityById(menuEntity);
        }else {
            menuEntity.setIsDelete(CodeDict.getValue(CodeTypeEnum.YES_NO.getCodeType(),CodeKeyEnum.K0.getKey()));
            menuEntity.setCreateTime(isUpdate ? menuEntity.getCreateTime() : DateUtils.getCurrentDateTime());
            sysMenuService.createEntity(menuEntity);
        }
        // 查询是否已经存在该菜单的角色菜单配置信息
        List<SysRoleMenuEntity> roleMenuList = sysRoleMenuService.findEntityByMenuId(menuEntity.getMenuId());
        // 待插入的角色菜单数据
        List<SysRoleMenuEntity> rmAddList = new ArrayList<>();
        // 待删除的角色菜单ID
        List<Long> deleteIdList = new ArrayList<>();
        // 不存在角色菜单数据
        if(CollectionUtils.isEmpty(roleMenuList)){
            // 不存在角色菜单数据且有新加角色，则需要新增角色菜单数据
            if(CollectionUtils.isNotEmpty(roleList)){
                for (SysRoleEntity roleEntity : roleList){
                    SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
                    roleMenuEntity.setRoleId(roleEntity.getRoleId());
                    roleMenuEntity.setMenuId(menuEntity.getMenuId());
                    roleMenuEntity.setCreateTime(DateUtils.getCurrentDateTime());
                    roleMenuEntity.setUpdateTime(DateUtils.getCurrentDateTime());
                    rmAddList.add(roleMenuEntity);
                }
            }
        }else {// 存在角色菜单数据
            // 存在角色菜单数据且有新加角色，则需要判断是新增还是删除角色菜单数据
            if(CollectionUtils.isNotEmpty(roleList)){
                // 存在角色菜单数据的角色ID集合
                List<Long> exitRoleIdList = roleMenuList.stream().map(SysRoleMenuEntity::getRoleId).collect(Collectors.toList());
                // 判断需要新增的数据
                for (SysRoleEntity roleEntity : roleList){
                    if(exitRoleIdList.contains(roleEntity.getRoleId())){
                        continue;
                    }
                    SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
                    roleMenuEntity.setRoleId(roleEntity.getRoleId());
                    roleMenuEntity.setMenuId(menuEntity.getMenuId());
                    roleMenuEntity.setCreateTime(DateUtils.getCurrentDateTime());
                    roleMenuEntity.setUpdateTime(DateUtils.getCurrentDateTime());
                    rmAddList.add(roleMenuEntity);
                }
                //待保存角色菜单数据的角色ID
                List<Long> saveIdList = roleList.stream().map(SysRoleEntity::getRoleId).collect(Collectors.toList());
                // 判断需要删除的数据
                for (SysRoleMenuEntity roleMenuEntity : roleMenuList){
                    if(!saveIdList.contains(roleMenuEntity.getRoleId())){
                        deleteIdList.add(roleMenuEntity.getSrmId());
                    }
                }
            }else {// 存在角色菜单数据且没有新加角色，则需要删除角色菜单数据
                deleteIdList = roleMenuList.stream().map(SysRoleMenuEntity::getSrmId).collect(Collectors.toList());
            }
        }
        // 新增角色菜单数据
        if(CollectionUtils.isNotEmpty(rmAddList)){
            sysRoleMenuService.saveBatch(rmAddList);
        }
        // 删除角色菜单数据
        if(CollectionUtils.isNotEmpty(deleteIdList)){
            //根据主键id集合删除角色菜单信息
            sysRoleMenuService.deleteByIds(deleteIdList);
        }
        //编辑的菜单是权限菜单，则需要更新redis中的权限菜单信息
        if(StringUtils.equals(menu.getMenuType(),CodeDict.getValue(CodeTypeEnum.MENU_TYPE.getCodeType(), CodeKeyEnum.K2.getKey()))){
            redisService.updateRedisUrlScope(menu.getModule());
        }
        response.setResponse(ResponseEnum.SUCCESS,"更新菜单成功");
        return response;
    }
    /**
     * <p>@Description 查询所有菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 16:59 </p>
     * @param menuDTO 查询条件da
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return Response<java.util.List < com.www.myblog.base.data.dto.SysMenuDTO>>
     */
    @Override
    public Response<List<SysMenuDTO>> findAllMenu(SysMenuDTO menuDTO,int pageNum, long pageSize) {
        if (menuDTO == null){
            menuDTO = new SysMenuDTO();
        }
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 5 : pageSize;
        Page<SysMenuDTO> page = new Page<>(pageNum,pageSize);
        page = sysMenuMapper.findAllMenu(page,menuDTO);
        List<SysMenuDTO> userList =  page.getRecords();
        Response<List<SysMenuDTO>> response = new Response<>(ResponseEnum.SUCCESS,userList);
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        return response;
    }
}
