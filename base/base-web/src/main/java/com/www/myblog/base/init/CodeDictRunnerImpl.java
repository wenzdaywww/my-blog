package com.www.myblog.base.init;

import com.www.common.config.code.CodeDict;
import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constant.RedisCommonContant;
import com.www.common.pojo.dto.code.CodeDTO;
import com.www.myblog.base.data.mapper.CodeDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private CodeDataMapper codeDataMapper;

    /**
     * <p>@Description 启动自加载数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 16:05 </p>
     * @param args
     * @return void
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initCodeData();
    }
    /**
     * <p>@Description 初始化code数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:19 </p>
     * @return void
     */
    public void initCodeData(){
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
}
