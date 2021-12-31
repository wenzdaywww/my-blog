package com.www.myblog.base.init;

import com.www.common.pojo.constant.RedisCommonContant;
import com.www.common.pojo.dto.ScopeDTO;
import com.www.common.config.redis.RedisOperation;
import com.www.myblog.base.data.constants.RedisKeyConstant;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
    private SysMenuMapper sysMenuMapper;
    /**
     * <p>@Description 启动执行方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/26 23:19 </p>
     * @param args
     * @return void
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean isWait = true; //是否等待获取分布式锁
        String value = UUID.randomUUID().toString();
        while (isWait){
            try {
                if(RedisOperation.lock(RedisKeyConstant.URL_SCOPE_LOCK, value)){
                    isWait = false;
                    //删除所有资源服务id的scope数据
                    RedisOperation.deleteFuzzyKey(RedisCommonContant.URL_SCOPE_PREFIX + "*");
                    List<ScopeDTO> scopeList = sysMenuMapper.findUrlScopes(null);
                    if(CollectionUtils.isNotEmpty(scopeList)){
                        for (ScopeDTO scopeDTO : scopeList){
                            //资源服务ID的url的scope保存到redis中
                            RedisOperation.listSet(RedisCommonContant.URL_SCOPE_PREFIX + scopeDTO.getResourceId(),scopeDTO);
                        }
                        log.info("启动加载资源服务ID的url的scope数据{}条",scopeList.size());
                    }
                }
            }catch (Exception e){
                isWait = false;
                log.info("查所询有请求权限，发生异常：{}",e.getMessage());
            }finally {
                // 释放锁
                RedisOperation.unlock(RedisKeyConstant.URL_SCOPE_LOCK,value);
            }
        }
    }
}
