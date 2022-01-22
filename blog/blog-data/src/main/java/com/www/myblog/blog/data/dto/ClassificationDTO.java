package com.www.myblog.blog.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@Description 博客分类类型 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
public class ClassificationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 分类主键
    */
    private Long classId;
    /**
     * 分类编码
     */
    private String classCode;
    /**
    * 分类名称
    */
    private String className;

}