package com.www.myblog.task.service;

/**
 * <p>@Description redis业务处理接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/7 21:13 </p>
 */
public interface IRedisService {
    /**
     * <p>@Description 整点更新博客统计量数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:20 </p>
     * @return void
     */
    void updateBlogNum();
}
