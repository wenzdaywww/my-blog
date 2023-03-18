package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.common.data.enums.ResponseEnum;
import com.www.common.data.response.Response;
import com.www.myblog.base.data.dto.MonitorInfoDTO;
import com.www.myblog.base.data.entity.MonitorInfoEntity;
import com.www.myblog.base.data.mapper.MonitorInfoMapper;
import com.www.myblog.base.service.entity.IMonitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description MONITOR_INFO表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class MonitorInfoServiceImpl extends ServiceImpl<MonitorInfoMapper, MonitorInfoEntity> implements IMonitorInfoService {
    @Autowired
    private MonitorInfoMapper monitorInfoMapper;
    /**
     * <p>@Description 查询监控信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/7/2 14:21 </p>
     * @param name 监控名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return com.www.common.data.dto.response.Response<java.util.List < com.www.myblog.base.data.dto.MonitorInfoDTO>>
     */
    @Override
    public Response<List<MonitorInfoDTO>> findMonitorInfo(String name, int pageNum, long pageSize) {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        Page<MonitorInfoDTO> page = new Page<>(pageNum,pageSize);
        page = monitorInfoMapper.findMonitorInfo(page,name);
        List<MonitorInfoDTO> monitorList =  page.getRecords();
        Response<List<MonitorInfoDTO>> response = new Response<>(ResponseEnum.SUCCESS,monitorList);
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        return response;
    }
}
