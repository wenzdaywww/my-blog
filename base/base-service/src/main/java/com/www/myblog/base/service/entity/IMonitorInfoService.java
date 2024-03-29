package com.www.myblog.base.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.www.common.data.response.Result;
import com.www.myblog.base.data.dto.MonitorInfoDTO;
import com.www.myblog.base.data.entity.MonitorInfoEntity;

import java.util.List;

/**
 * <p>@Description MONITOR_INFO表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface IMonitorInfoService extends IService<MonitorInfoEntity> {
    /**
     * <p>@Description 查询监控信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/7/2 14:21 </p>
     * @param name 监控名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return Response<List<MonitorInfoDTO>>
     */
    Result<List<MonitorInfoDTO>> findMonitorInfo(String name, int pageNum, long pageSize);
}
