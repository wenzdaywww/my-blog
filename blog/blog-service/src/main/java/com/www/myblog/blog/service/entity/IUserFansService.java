package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.UserFansEntity;

/**
 * <p>@Description 用户关注表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/29 15:44 </p>
 */
public interface IUserFansService {
    /**
     * <p>@Description 根据用户id和粉丝ID查询用户关注信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/29 15:47 </p>
     * @param userId 用户ID
     * @param fansId 粉丝ID
     * @return com.www.myblog.blog.data.entity.UserFansEntity
     */
    UserFansEntity findUserFansEntity(String userId,String fansId);
}
