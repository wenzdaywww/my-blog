package com.www.myblog.base.service.redis.impl;

import com.www.common.config.code.dto.CodeDTO;
import com.www.common.config.code.write.CodeRedisWriteHandler;
import com.www.common.config.oauth2.dto.ScopeDTO;
import com.www.common.config.redis.RedisOperation;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.data.properties.BaseProperties;
import com.www.myblog.base.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>@Description redis业务实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/4 12:01 </p>
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {
    /** 资源服务id **/
    @Value("${spring.application.name}")
    private String resourceId;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private BaseProperties baseProperties;
    @Autowired
    private CodeRedisWriteHandler codeRedisWriteHandler;


    /**
     * <p>@Description 编辑/删除的菜单是请求路径，则需要更新redis中的请求路径 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/15 21:07 </p>
     * @param resourceId 资源服务ID
     * @return
     */
    @Override
    public void updateRedisUrlScope(String resourceId) {
        String value = UUID.randomUUID().toString();
        try {
            if(RedisOperation.lock(baseProperties.getUrlScopeLock(), value,60)){
                RedisOperation.deleteKey(baseProperties.getUrlScopePrefix() + ":" + resourceId);
                List<ScopeDTO> scopeList = sysMenuMapper.findUrlScopes(resourceId);
                if(CollectionUtils.isNotEmpty(scopeList)){
                    for (ScopeDTO scopeDTO : scopeList){
                        //资源服务ID的url的scope保存到redis中
                        RedisOperation.listSet(baseProperties.getUrlScopePrefix() + ":" + resourceId,scopeDTO);
                    }
                }
            }
        }catch (Exception e){
            log.info("查所询有请求权限，发生异常：{}",e.getMessage());
        }finally {
            // 释放锁
            RedisOperation.unlock(baseProperties.getUrlScopeLock(),value);
        }
    }
    /**
     * <p>@Description 初始化redis中的scope数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 16:34 </p>
     * @return void
     */
    @Override
    public void initRedisUrlScope() {
        String value = UUID.randomUUID().toString();
        try {
            if(RedisOperation.lock(baseProperties.getUrlScopeLock(), value,60)){
                //删除所有资源服务id的scope数据
                RedisOperation.deleteFuzzyKey(baseProperties.getUrlScopePrefix() + ":" + "*");
                List<ScopeDTO> scopeList = sysMenuMapper.findUrlScopes(null);
                if(CollectionUtils.isNotEmpty(scopeList)){
                    for (ScopeDTO scopeDTO : scopeList){
                        //资源服务ID的url的scope保存到redis中
                        RedisOperation.listSet(baseProperties.getUrlScopePrefix() + ":" + scopeDTO.getResourceId(),scopeDTO);
                    }
                    log.info("启动加载资源服务ID的url的scope数据{}条",scopeList.size());
                }
            }
        }catch (Exception e){
            log.info("查询所有请求权限，发生异常：{}",e.getMessage());
        }finally {
            // 释放锁
            RedisOperation.unlock(baseProperties.getUrlScopeLock(),value);
        }
    }
    /**
     * <p>@Description 获取redis中的数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/4 12:03 </p>
     * @return java.util.Map<java.lang.String, java.util.Map < java.lang.String, com.www.common.config.code.dto.CodeDTO>>
     */
    @Override
    public Map<String, Map<String, CodeDTO>> getCodeData() {
        return codeRedisWriteHandler.getCodeData();
    }
}
