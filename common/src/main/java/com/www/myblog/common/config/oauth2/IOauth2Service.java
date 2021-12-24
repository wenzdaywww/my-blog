package com.www.myblog.common.config.oauth2;

import com.www.myblog.common.pojo.ScopeDTO;

import java.util.List;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/24 22:36 </p>
 */
public interface IOauth2Service {
    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围
     *  scope配置在此方法查询，用户拥有的角色不在此处查询，统一在使用@PreAuthorize注解的方法上校验</p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    List<ScopeDTO> findUrlScope();
}
