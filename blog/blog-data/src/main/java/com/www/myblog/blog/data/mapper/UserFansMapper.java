package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.UserFansEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>@Description 用户粉丝信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface UserFansMapper extends BaseMapper<UserFansEntity> {
}