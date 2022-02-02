package com.www.myblog.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("COLLECT_GROUP")
public class CollectGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 收藏分组主键
    */
    @TableId(value = "CG_ID",type = IdType.AUTO)
    private Long cgId;
    /**
    * 收藏分组名称
    */
    @TableField("COLLECT_NAME")
    private String collectName;
    /**
    * 用户ID
    */
    @TableField("USER_ID")
    private String userId;
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