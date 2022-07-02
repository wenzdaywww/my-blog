package com.www.myblog.base.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("MONITOR_INFO")
public class MonitorInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 监控主键
    */
    @TableId(value = "MONITOR_ID",type = IdType.AUTO)
    private Long monitorId;
    /**
    * 监控名称
    */
    @TableField("MONITOR_NAME")
    private String monitorName;
    /**
    * 监控地址
    */
    @TableField("MONITOR_ADDR")
    private Short monitorAddr;
    /**
    * 更新时间
    */
    @TableField("UPDATE_TIME")
    private Date updateTime;
    /**
    * 创建时间
    */
    @TableField("CREATE_TIME")
    private Date createTime;
}