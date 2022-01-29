package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>@Description 博客标签信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@Accessors(chain = true)//开启链式编程
@TableName("BLOG_TAG")
public class BlogTagEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 博客标签主键
    */
    @TableId(value = "BT_ID",type = IdType.AUTO)
    private Long btId;
    /**
    * 博客ID
    */
    @TableField("BLOG_ID")
    private Long blogId;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;
    /**
    * 标签ID
    */
    @TableField("TAG_ID")
    private Long tagId;
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