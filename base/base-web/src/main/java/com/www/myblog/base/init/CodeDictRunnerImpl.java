package com.www.myblog.base.init;

import com.www.myblog.base.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 数据字典加载 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/1 16:04 </p>
 */
@Slf4j
@Component
public class CodeDictRunnerImpl implements ApplicationRunner {
    @Autowired
    private IRedisService redisService;

    /**
     * <p>@Description 启动自加载数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 16:05 </p>
     * @param args
     * @return void
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisService.initCodeData();
    }
}
