package com.www.myblog.base.service.menu.impl;

import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constant.RedisCommonContant;
import com.www.common.pojo.dto.security.ScopeDTO;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.service.menu.IUrlScopeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/1 16:35 </p>
 */
@Slf4j
@Service
public class UrlScopeServiceImpl implements IUrlScopeService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * <p>@Description 编辑/删除的菜单是请求路径，则需要更新redis中的请求路径 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/15 21:07 </p>
     *
     * @param resourceId 资源服务ID
     * @return
     */
    @Override
    public void updateRedisUrlScope(String resourceId) {
        boolean isWait = true; //是否等待获取分布式锁
        String value = UUID.randomUUID().toString();
        while (isWait){
            try {
                if(RedisOperation.lock(RedisCommonContant.URL_SCOPE_LOCK, value,60)){
                    isWait = false;
                    RedisOperation.deleteKey(RedisCommonContant.URL_SCOPE_PREFIX + resourceId);
                    List<ScopeDTO> scopeList = sysMenuMapper.findUrlScopes(resourceId);
                    if(CollectionUtils.isNotEmpty(scopeList)){
                        for (ScopeDTO scopeDTO : scopeList){
                            //资源服务ID的url的scope保存到redis中
                            RedisOperation.listSet(RedisCommonContant.URL_SCOPE_PREFIX + resourceId,scopeDTO);
                        }
                    }
                }
            }catch (Exception e){
                log.info("查所询有请求权限，发生异常：{}",e.getMessage());
            }finally {
                // 释放锁
                RedisOperation.unlock(RedisCommonContant.URL_SCOPE_LOCK,value);
            }
        }
    }

    /**
     * <p>@Description 初始化redis中的scope数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 16:34 </p>
     *
     * @return void
     */
    @Override
    public void initRedisUrlScope() {
        boolean isWait = true; //是否等待获取分布式锁
        String value = UUID.randomUUID().toString();
        while (isWait){
            try {
                if(RedisOperation.lock(RedisCommonContant.URL_SCOPE_LOCK, value,60)){
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
                log.info("查询所有请求权限，发生异常：{}",e.getMessage());
            }finally {
                // 释放锁
                RedisOperation.unlock(RedisCommonContant.URL_SCOPE_LOCK,value);
            }
        }
    }
}
