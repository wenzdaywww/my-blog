package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private static final long serialVersionUID = 1L;
    /**
    * 博客分类主键
    */
    @TableId(value = "BC_ID",type = IdType.AUTO)
    private Long bcId;
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
    * 分类ID
    */
    @TableField("CLASS_ID")
    private Long classId;
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