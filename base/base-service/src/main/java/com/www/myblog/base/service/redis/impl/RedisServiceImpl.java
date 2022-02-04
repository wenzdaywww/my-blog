package com.www.myblog.base.service.redis.impl;

import com.www.common.config.code.CodeDict;
import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constant.RedisCommonContant;
import com.www.common.pojo.dto.code.CodeDTO;
import com.www.common.pojo.dto.security.ScopeDTO;
import com.www.myblog.base.data.mapper.CodeDataMapper;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private CodeDataMapper codeDataMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;


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
            if(RedisOperation.lock(RedisCommonContant.URL_SCOPE_LOCK, value,60)){
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
            if(RedisOperation.lock(RedisCommonContant.URL_SCOPE_LOCK, value,60)){
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
            log.info("查询所有请求权限，发生异常：{}",e.getMessage());
        }finally {
            // 释放锁
            RedisOperation.unlock(RedisCommonContant.URL_SCOPE_LOCK,value);
        }
    }
    /**
     * <p>@Description 初始化code数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:19 </p>
     * @return void
     */
    @Override
    public void initCodeData() {
        boolean isWait = true; //是否等待获取分布式锁
        String value = UUID.randomUUID().toString();
        while (isWait){
            try {
                if(RedisOperation.lock(RedisCommonContant.CODE_DATA_LOCK, value,60)){
                    isWait = false;
                    List<CodeDTO> codeList = codeDataMapper.findAllCodeData();
                    if(CollectionUtils.isNotEmpty(codeList)){
                        Map<String, Map<String, CodeDTO>> codeMap = new HashMap<>();
                        for (CodeDTO dto : codeList){
                            Map<String, CodeDTO> keyMap = new HashMap<>();
                            if(codeMap.containsKey(dto.getType())){
                                keyMap = codeMap.get(dto.getType());
                                keyMap.put(dto.getCodeKey(),dto);
                            }else {
                                keyMap.put(dto.getCodeKey(),dto);
                                codeMap.put(dto.getType(),keyMap);
                            }
                        }
                        if (MapUtils.isNotEmpty(codeMap)){
                            RedisOperation.deleteKey(RedisCommonContant.CODE_DATA);
                            for (String key : codeMap.keySet()){
                                RedisOperation.hashSet(RedisCommonContant.CODE_DATA,key,codeMap.get(key));
                            }
                        }
                        CodeDict.initCode(codeMap);
                        log.info("启动加载code_data数据{}条",codeList.size());
                    }
                }
            }catch (Exception e){
                isWait = false;
                log.info("查询所有CODE_DATA，发生异常：{}",e.getMessage());
            }finally {
                // 释放锁
                RedisOperation.unlock(RedisCommonContant.CODE_DATA_LOCK,value);
            }
        }
    }
    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    @Override
    public List<ScopeDTO> findUrlScope() {
        return (List<ScopeDTO>) RedisOperation.listGet(RedisCommonContant.URL_SCOPE_PREFIX + resourceId);
    }
    /**
     * <p>@Description 获取redis中的数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/4 12:03 </p>
     * @return java.util.Map<java.lang.String, java.util.Map < java.lang.String, com.www.common.pojo.dto.code.CodeDTO>>
     */
    @Override
    public Map<String, Map<String, CodeDTO>> getCodeData() {
        return (Map<String,Map<String, CodeDTO>>) RedisOperation.hashGet(RedisCommonContant.CODE_DATA);
    }
}
