package com.www.myblog.base.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.base.data.dto.MonitorInfoDTO;
import com.www.myblog.base.data.entity.MonitorInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>@Description 监控信息Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Mapper
public interface MonitorInfoMapper extends BaseMapper<MonitorInfoEntity> {
    /**
     * <p>@Description 查询监控信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/7/2 14:33 </p>
     * @param page 分页信息
     * @param name 监控名称
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.www.myblog.base.data.dto.MonitorInfoDTO>
     */
    Page<MonitorInfoDTO> findMonitorInfo(Page<MonitorInfoDTO> page,@Param("name") String name);
}