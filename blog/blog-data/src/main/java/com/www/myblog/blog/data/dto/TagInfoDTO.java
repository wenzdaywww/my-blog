package com.www.myblog.blog.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@Description 标签信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
public class TagInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 标签ID
    */
    private Long tagId;
    /**
    * 标签名称
    */
    private String tagName;
    /**
    * 标签博客数量
    */
    private String tagNum;

}