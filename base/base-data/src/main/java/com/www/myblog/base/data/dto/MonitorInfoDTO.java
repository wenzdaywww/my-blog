package com.www.myblog.base.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 监控信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Data
@Accessors(chain = true)//开启链式编程
public class MonitorInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 监控主键
    */
    private Long monitorId;
    /**
    * 监控名称
    */
    private String monitorName;
    /**
    * 监控地址
    */
    private String monitorAddr;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 创建时间
    */
    private Date createTime;
}