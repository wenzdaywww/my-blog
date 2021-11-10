package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.entity.UserFriendsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 用户好友信息Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Mapper
public interface UserFriendsMapper extends BaseMapper<UserFriendsEntity> {
    /**
     * <p>@Description 根据主键删除用户好友信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:47 </p>
     * @param friendsId 主键
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long friendsId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:47 </p>
     * @param record 用户好友信息
     * @return int 插入条数
     */
    int insert(UserFriendsEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:47 </p>
     * @param record 用户好友信息
     * @return int 插入条数
     */
    int insertNotNull(UserFriendsEntity record);
    /**
     * <p>@Description 根据主键查询用户好友信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:47 </p>
     * @param friendsId 主键
     * @return com.www.myblog.admin.data.entity.UserFriendsEntity 用户好友信息
     */
    UserFriendsEntity selectByPrimaryKey(Long friendsId);
    /**
     * <p>@Description 根据主键更新用户好友信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:47 </p>
     * @param record 用户好友信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(UserFriendsEntity record);
}