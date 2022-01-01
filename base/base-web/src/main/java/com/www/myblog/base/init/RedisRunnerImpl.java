package com.www.myblog.base.init;

import com.www.myblog.base.service.menu.IUrlScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 启动加载reids数据 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/26 23:17 </p>
 */
@Slf4j
@Component
public class RedisRunnerImpl implements ApplicationRunner {
    @Autowired
    private IUrlScopeService urlScopeService;

    /**
     * <p>@Description 启动执行方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/26 23:19 </p>
     * @param args
     * @return void
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        urlScopeService.initRedisUrlScope();
    }
}
