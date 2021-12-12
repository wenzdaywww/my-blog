package com.www.myblog.common.pojo;

import lombok.Data;

/**
 * <p>@Description 请求权限DTO </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/12 15:57 </p>
 */
@Data
public class AuthorityDTO {
    /** 请求路径 **/
    private String url;
    /** 授权角色 **/
    private String role;
}
