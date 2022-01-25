package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.entity.GroupInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 分组信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface GroupInfoMapper extends BaseMapper<GroupInfoEntity> {
    /**
     * <p>@Description 查询用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:34 </p>
     * @param userId 用户ID
     * @return java.util.List<com.www.myblog.blog.data.dto.BlogGroupDTO>
     */
    List<BlogGroupDTO> findBlogGroup(@Param("userId") String userId);
}