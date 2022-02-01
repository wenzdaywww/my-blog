package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.base.data.entity.SysMenuEntity;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.service.entity.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description SYS_MENU表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * <p>@Description 新增菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:06 </p>
     * @param menuEntity 菜单信息
     * @return boolean true新增成功，false新增失败
     */
    @Override
    public boolean createEntity(SysMenuEntity menuEntity) {
        if(menuEntity == null){
            return false;
        }
        int count = sysMenuMapper.insert(menuEntity);
        return count > 0;
    }
    /**
     * <p>@Description 根据菜单id更新菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:04 </p>
     * @param menuEntity 菜单信息
     * @return boolean true更新成功，false更新失败
     */
    @Override
    public boolean updateEntityById(SysMenuEntity menuEntity) {
        if(menuEntity == null){
            return false;
        }
        int count = sysMenuMapper.updateById(menuEntity);
        return count > 0;
    }
    /**
     * <p>@Description 根据菜单id删除菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:00 </p>
     * @param menuId 菜单id
     * @return boolean true删除成功，false删除失败
     */
    @Override
    public boolean deleteById(Long menuId) {
        if(menuId == null){
            return false;
        }
        int count = sysMenuMapper.deleteById(menuId);
        return count > 0;
    }
    /**
     * <p>@Description 根据菜单id查询菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:58 </p>
     * @param menuId 菜单id
     * @return com.www.myblog.base.data.entity.SysMenuEntity 菜单信息
     */
    @Override
    public SysMenuEntity findById(Long menuId) {
        if(menuId == null){
            return null;
        }
        return sysMenuMapper.selectById(menuId);
    }
}
