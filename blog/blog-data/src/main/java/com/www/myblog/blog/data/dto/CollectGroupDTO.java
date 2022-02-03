package com.www.myblog.blog.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 收藏分组信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@Accessors(chain = true)//开启链式编程
public class CollectGroupDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 当前页数 **/
    private Integer pageNum;
    /** 页面条数 **/
    private Long pageSize;
    /**
    * 收藏分组主键
    */
    private Long cgId;
    /**
    * 收藏分组名称
    */
    private String collectName;
    /**
    * 用户ID
    */
    private String userId;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 创建时间
    */
    private Date createTime;

}