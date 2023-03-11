package com.www.myblog.base.service.redis;


import com.www.common.config.code.dto.CodeDTO;
import com.www.common.config.oauth2.dto.ScopeDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>@Description redis业务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/4 12:01 </p>
 */
public interface IRedisService {
    /**
     * <p>@Description 编辑/删除的菜单是请求路径，则需要更新redis中的请求路径 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/15 21:07 </p>
     * @param resourceId 资源服务ID
     * @return
     */
    void updateRedisUrlScope(String resourceId);
    /**
     * <p>@Description 初始化redis中的scope数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 16:34 </p>
     * @return void
     */
    void initRedisUrlScope();
    /**
     * <p>@Description 初始化code数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:19 </p>
     * @return void
     */
    void initCodeData();
    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    List<ScopeDTO> findUrlScope();
    /**
     * <p>@Description 获取redis中的数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/4 12:03 </p>
     * @return java.util.Map<java.lang.String, java.util.Map < java.lang.String, com.www.common.config.code.dto.CodeDTO>>
     */
    Map<String, Map<String, CodeDTO>> getCodeData();
}
