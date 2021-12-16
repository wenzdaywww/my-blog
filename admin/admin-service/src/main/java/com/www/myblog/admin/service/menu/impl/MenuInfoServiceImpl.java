package com.www.myblog.admin.service.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.admin.data.constants.RedisKeyConstant;
import com.www.myblog.admin.data.dto.SysMenuDTO;
import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.admin.data.entity.SysMenuEntity;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysRoleMenuEntity;
import com.www.myblog.admin.data.enums.CommonEnum;
import com.www.myblog.admin.data.mapper.SysMenuMapper;
import com.www.myblog.admin.data.mapper.SysRoleMenuMapper;
import com.www.myblog.admin.service.entity.ISysRoleMenuService;
import com.www.myblog.admin.service.entity.ISysRoleService;
import com.www.myblog.admin.service.menu.IMenuInfoService;
import com.www.myblog.common.pojo.AuthorityDTO;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import com.www.myblog.common.utils.DateUtils;
import com.www.myblog.common.utils.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>@Description 菜单service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 20:42 </p>
 */
@Service
public class MenuInfoServiceImpl implements IMenuInfoService {
    private static Logger LOG = LoggerFactory.getLogger(MenuInfoServiceImpl.class);
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;


    /**
     * <p>@Description 删除菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 23:57 </p>
     * @param menuId 菜单ID
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> deleteMenu(Long menuId) {
        QueryWrapper<SysMenuEntity> menuWrapper = new QueryWrapper<>();
        menuWrapper.lambda().eq(SysMenuEntity::getMenuId,menuId);
        sysMenuMapper.delete(menuWrapper);
        QueryWrapper<SysRoleMenuEntity> roleWrapper = new QueryWrapper<>();
        roleWrapper.lambda().eq(SysRoleMenuEntity::getMenuId,menuId);
        int count = sysRoleMenuMapper.delete(roleWrapper);
        if(count != 0){
            this.updateRedisAuthority();
            return new ResponseDTO<>(ResponseEnum.SUCCESS,"删除菜单成功");
        }else {
            return new ResponseDTO<>(ResponseEnum.FAIL,"删除菜单失败");
        }
    }
    /**
     * <p>@Description 修改或创建菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 19:58 </p>
     * @param menu 菜单信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> updateOrSave(SysMenuDTO menu) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(menu == null || StringUtils.isAnyBlank(menu.getMenuCode(),menu.getMenuUrl(),menu.getMenuType())
            || !StringUtils.equalsAny(menu.getMenuType(), CommonEnum.MENU_TYPE_1.getCode(),CommonEnum.MENU_TYPE_2.getCode())){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新菜单失败，信息有误");
            return responseDTO;
        }
        SysMenuEntity parentEntity = null;
        SysMenuEntity menuEntity = null;
        List<SysRoleEntity> roleList = null;
        // 查询菜单是否存在
        if(menu.getMenuId() != null){
            menuEntity = sysMenuMapper.selectById(menu.getMenuId());
        }
        boolean isUpdate = menuEntity != null; //是否是更新
        // 查询父级菜单是否存在
        if(menu.getParentId() != null){
            parentEntity = sysMenuMapper.selectById(menu.getParentId());
            if(parentEntity == null){
                responseDTO.setResponseCode(ResponseEnum.FAIL,"更新菜单失败，父级菜单不存在");
                return responseDTO;
            }
        }
        if(StringUtils.isNotBlank(menu.getRoleName())){
            String[] roleArr = menu.getRoleName().split(",");
            roleList = sysRoleService.findRoleEntityByName(roleArr);
            if(CollectionUtils.isEmpty(roleList)){
                responseDTO.setResponseCode(ResponseEnum.FAIL,"更新菜单失败，角色不存在");
                return responseDTO;
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
        menuEntity.setSysUpdateTime(DateUtils.getCurrentDateTime());
        if(isUpdate){
            sysMenuMapper.updateById(menuEntity);
        }else {
            menuEntity.setIsDelete(CommonEnum.NO_0.getCode());
            menuEntity.setSysCreateTime(isUpdate ? menuEntity.getSysCreateTime() : DateUtils.getCurrentDateTime());
            sysMenuMapper.insert(menuEntity);
        }
        // 查询是否已经存在该菜单的角色菜单配置信息
        QueryWrapper<SysRoleMenuEntity> rmWrapper = new QueryWrapper<>();
        rmWrapper.lambda().eq(SysRoleMenuEntity::getMenuId,menuEntity.getMenuId());
        List<SysRoleMenuEntity> roleMenuList = sysRoleMenuMapper.selectList(rmWrapper);
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
                    roleMenuEntity.setSysCreateTime(DateUtils.getCurrentDateTime());
                    roleMenuEntity.setSysUpdateTime(DateUtils.getCurrentDateTime());
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
                    roleMenuEntity.setSysCreateTime(DateUtils.getCurrentDateTime());
                    roleMenuEntity.setSysUpdateTime(DateUtils.getCurrentDateTime());
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
            QueryWrapper<SysRoleMenuEntity> delWrapper = new QueryWrapper<>();
            delWrapper.lambda().in(SysRoleMenuEntity::getSrmId,deleteIdList);
            sysRoleMenuMapper.delete(delWrapper);
        }
        //编辑的菜单是权限菜单，则需要更新redis中的权限菜单信息
        if(StringUtils.equals(menu.getMenuType(),CommonEnum.MENU_TYPE_2.getCode())){
            this.updateRedisAuthority();
        }
        responseDTO.setResponseCode(ResponseEnum.SUCCESS,"更新菜单成功");
        return responseDTO;
    }
    /**
     * <p>@Description 编辑的菜单是权限菜单，则需要更新redis中的权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/15 21:07 </p>
     * @return
     */
    private void updateRedisAuthority(){
        boolean isWait = true; //是否等待获取分布式锁
        String value = UUID.randomUUID().toString();
        while (isWait){
            try {
                if(RedisUtils.lock(RedisKeyConstant.AUTHORITY_MENU_LOCK, value)){
                    isWait = false;
                    RedisUtils.deleteKey(RedisKeyConstant.AUTHORITY_MENU);
                    List<SysRoleMenuDTO> menuList = sysMenuMapper.findAllSecurityMenu("admin-security");
                    if(CollectionUtils.isNotEmpty(menuList)){
                        for (SysRoleMenuDTO menuDTO : menuList){
                            AuthorityDTO authDTO = new AuthorityDTO();
                            authDTO.setUrl(menuDTO.getMenuUrl());
                            authDTO.setRole(menuDTO.getRoleName());
                            //将所有请求权限保存到redis中
                            RedisUtils.listSet(RedisKeyConstant.AUTHORITY_MENU,authDTO);
                        }
                    }
                }
            }catch (Exception e){
                LOG.info("查所询有请求权限，发生异常：{}",e.getMessage());
            }finally {
                // 释放锁
                RedisUtils.unlock(RedisKeyConstant.AUTHORITY_MENU_LOCK,value);
            }
        }
    }
    /**
     * <p>@Description 查询所有菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 16:59 </p>
     * @param menuDTO 查询条件da
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysMenuDTO>>
     */
    @Override
    public ResponseDTO<List<SysMenuDTO>> findAllMenu(SysMenuDTO menuDTO,int pageNum, int pageSize) {
        if (menuDTO == null){
            menuDTO = new SysMenuDTO();
        }
        Page<SysMenuDTO> page = new Page<>(pageNum,pageSize);
        page = sysMenuMapper.findAllMenu(page,menuDTO);
        List<SysMenuDTO> userList =  page.getRecords();
        ResponseDTO<List<SysMenuDTO>> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,userList);
        responseDTO.setPageNum(pageNum);
        responseDTO.setPageSize(pageSize);
        responseDTO.setTotalNum(page.getTotal());
        return responseDTO;
    }
}
