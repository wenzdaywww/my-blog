package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 博客分类类型 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@TableName("CLASSIFY")
public class ClassifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 分类主键
    */
    @TableId(value = "COMMENT_ID",type = IdType.AUTO)
    private Long classId;
    /**
    * 分类名称
    */
    @TableField("CLASS_NAME")
    private String className;
    /**
    * 分类描述
    */
    @TableField("CLASS_ALIAS")
    private String classAlias;
    /**
    * 更新时间
    */
    @TableField("SYS_UPDATE_TIME")
    private Date sysUpdateTime;
    /**
    * 创建时间
    */
    @TableField("SYS_CREATE_TIME")
    private Date sysCreateTime;

}