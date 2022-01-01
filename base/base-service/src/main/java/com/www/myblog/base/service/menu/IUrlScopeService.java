package com.www.myblog.base.service.menu;

/**
 * <p>@Description redis中的url的scope数据业务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/1 16:34 </p>
 */
public interface IUrlScopeService {
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
}
