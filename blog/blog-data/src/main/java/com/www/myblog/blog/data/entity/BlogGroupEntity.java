package com.www.myblog.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 博客分组信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@TableName("BLOG_GROUP")
public class BlogGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 博客分组主键
    */
    @TableId(value = "BG_ID",type = IdType.AUTO)
    private Long bgId;
    /**
     * 分组ID
     */
    @TableField("GROUP_ID")
    private Long groupId;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;
    /**
    * 博客ID
    */
    @TableField("BLOG_ID")
    private Long blogId;
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