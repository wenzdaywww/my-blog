package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 博客分类信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@TableName("BLOG_CLASS")
public class BlogClassEntity implements Serializable {
    /**
    * 博客分类主键
    */
    private Long bcId;

    /**
    * 博客ID
    */
    private Long blogId;

    /**
    * 分类ID
    */
    private Long classId;

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