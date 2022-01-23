package com.www.myblog.blog.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>@Description 博主信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:05 </p>
 */
@Data
@Accessors(chain = true)//开启链式编程
public class AuthorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 博主ID
    */
    private String userId;
    /**
    * 博主名称
    */
    private String userName;
    /**
    * 博主头像
    */
    private String photo;
    /**
    * 博主码龄
    */
    private String age;
    /**
    * 博客数量
    */
    private Integer blogs;
    /**
    * 粉丝数量
    */
    private Integer fans;
    /**
    * 获赞数量
    */
    private Integer likes;
    /**
    * 关注数
    */
    private Integer follows;
    /**
    * 评论数量
    */
    private Integer comments;
    /**
    * 收藏数量
    */
    private Integer collects;

}