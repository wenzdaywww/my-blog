package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>@Description 博客分类类型 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
public class ClassifyEntity implements Serializable {
    /**
    * 分类主键
    */
    private Long classId;

    /**
    * 分类名称
    */
    private String className;

    /**
    * 分类描述
    */
    private String classAlias;

    /**
    * 更新时间
    */
    private Date sysUpdateTime;

    /**
    * 创建时间
    */
    private Date sysCreateTime;

    private static final long serialVersionUID = 1L;
}