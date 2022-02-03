package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.CollectGroupDTO;
import com.www.myblog.blog.data.entity.CollectGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 收藏分组信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface CollectGroupMapper extends BaseMapper<CollectGroupEntity> {
    /**
     * <p>@Description 查询收藏夹列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:29 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    List<CollectGroupDTO> findCollectGroup(@Param("userId") String userId);
}