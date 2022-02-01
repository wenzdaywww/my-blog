package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.base.data.entity.SysRoleMenuEntity;
import com.www.myblog.base.data.mapper.SysRoleMenuMapper;
import com.www.myblog.base.service.entity.ISysRoleMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description SYS_ROLE_MENU表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenuEntity> implements ISysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * <p>@Description 根据主键id集合删除角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:17 </p>
     * @param idList 主键id集合
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteByIds(List<Long> idList) {
        if(CollectionUtils.isEmpty(idList)){
            return false;
        }
        QueryWrapper<SysRoleMenuEntity> delWrapper = new QueryWrapper<>();
        delWrapper.lambda().in(SysRoleMenuEntity::getSrmId,idList);
        int count = sysRoleMenuMapper.delete(delWrapper);
        return count > 0;
    }

    /**
     * <p>@Description 根据菜单id查询角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:14 </p>
     * @param menuId 菜单id
     * @return java.util.List<com.www.myblog.base.data.entity.SysRoleMenuEntity> 角色菜单信息
     */
    @Override
    public List<SysRoleMenuEntity> findEntityByMenuId(Long menuId) {
        if(menuId == null){
            return null;
        }
        QueryWrapper<SysRoleMenuEntity> rmWrapper = new QueryWrapper<>();
        rmWrapper.lambda().eq(SysRoleMenuEntity::getMenuId,menuId);
        return sysRoleMenuMapper.selectList(rmWrapper);
    }
    /**
     * <p>@Description 根据菜单id删除角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:10 </p>
     * @param menuId 菜单id
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteByMenuId(Long menuId) {
        if(menuId == null){
            return false;
        }
        QueryWrapper<SysRoleMenuEntity> roleWrapper = new QueryWrapper<>();
        roleWrapper.lambda().eq(SysRoleMenuEntity::getMenuId,menuId);
        int count = sysRoleMenuMapper.delete(roleWrapper);
        return count > 0;
    }
}
