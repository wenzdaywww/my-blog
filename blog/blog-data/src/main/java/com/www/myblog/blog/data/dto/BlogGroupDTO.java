package com.www.myblog.blog.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@Description 博客分组信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
public class BlogGroupDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 博客分组主键
    */
    private Long bgId;
    /**
    * 博客分组名称
    */
    private String groupName;
    /**
    * 分组博客数量
    */
    private String groupNum;
    /**
    * 用户ID
    */
    private Long userId;

}