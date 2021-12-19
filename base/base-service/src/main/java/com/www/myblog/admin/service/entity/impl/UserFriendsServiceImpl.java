package com.www.myblog.admin.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.admin.data.entity.UserFriendsEntity;
import com.www.myblog.admin.data.mapper.UserFriendsMapper;
import com.www.myblog.admin.service.entity.IUserFriendsService;
import org.springframework.stereotype.Service;

/**
 * <p>@Description USER_FRIENDS表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class UserFriendsServiceImpl extends ServiceImpl<UserFriendsMapper, UserFriendsEntity> implements IUserFriendsService {
}
