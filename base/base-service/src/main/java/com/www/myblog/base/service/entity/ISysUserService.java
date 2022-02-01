package com.www.myblog.base.service.entity;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.www.myblog.base.data.entity.SysUserEntity;

import java.util.List;

/**
 * <p>@Description SYS_USER表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysUserService extends IService<SysUserEntity> {
    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:35 </p>
     * @param entity 用户信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(SysUserEntity entity);
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    boolean updateEntity(UpdateWrapper<SysUserEntity> wrapper);
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param entity 更新内容
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    boolean updateEntity(SysUserEntity entity,UpdateWrapper<SysUserEntity> wrapper);
    /**
     * <p>@Description 根据用户ID集合查询用户信息条数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:24 </p>
     * @param userList 用户id集合
     * @return int 用户信息条数
     */
    int findUserCountById(List<String> userList);
    /**
     * <p>@Description 根据用户ID集合查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:21 </p>
     * @param userList 用户id集合
     * @return java.util.List<com.www.myblog.base.data.entity.SysUserEntity> 用户信息集合
     */
    List<SysUserEntity> findUserEntityById(List<String> userList);
    /**
     * <p>@Description 根据用户ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param userId 用户ID
     * @return com.www.myblog.base.data.entity.SysUserEntity 用户信息
     */
    SysUserEntity findUserEntityById(String userId);
}
