package com.www.myblog.blog.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 博客分类类型 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@TableName("CLASSIFICATION")
public class ClassificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 分类主键
    */
    @TableId(value = "COMMENT_ID",type = IdType.AUTO)
    private Long classId;
    /**
     * 分类编码
     */
    @TableField("CLASS_CODE")
    private String classCode;
    /**
    * 分类名称
    */
    @TableField("CLASS_NAME")
    private String className;
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