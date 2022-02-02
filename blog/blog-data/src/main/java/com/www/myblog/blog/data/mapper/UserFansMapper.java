package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.entity.UserFansEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>@Description 用户粉丝信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface UserFansMapper extends BaseMapper<UserFansEntity> {
    /**
     * <p>@Description 获取粉丝列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 23:00 </p>
     * @param page 分页信息
     * @param userId 用户id
     * @return java.util.List<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    Page<AuthorDTO> findFansList(Page<AuthorDTO> page, @Param("userId") String userId);
    /**
     * <p>@Description 查询关注列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 23:00 </p>
     * @param page 分页信息
     * @param userId 用户id
     * @return java.util.List<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    Page<AuthorDTO> findFollowList(Page<AuthorDTO> page, @Param("userId") String userId);
}