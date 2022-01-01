package com.www.common.config.code.init;

import com.www.common.config.code.CodeDict;
import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constant.RedisCommonContant;
import com.www.common.pojo.dto.code.CodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>@Description 数据字典加载 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/1 16:04 </p>
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "com.www.common.code",name = "enable")
public class CodeDictRunner implements ApplicationRunner {
    /**
     * <p>@Description 启动自加载数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 16:05 </p>
     * @param args
     * @return void
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动自加载数据字典数据");
        this.initCodeData();
    }
    /**
     * <p>@Description 初始化code数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:19 </p>
     * @return void
     */
    public void initCodeData(){
        try {
            Map<String,Map<String, CodeDTO>> codeMap = (Map<String,Map<String, CodeDTO>>)RedisOperation.hashGet(RedisCommonContant.CODE_DATA);
            if(MapUtils.isNotEmpty(codeMap)){
                CodeDict.initCode(codeMap);
                log.info("加载code_type数据{}条",codeMap.size());
            }else {
                log.info("加载code_type失败，redis中不存在数据");
            }
        }catch (Exception e){
            log.error("加载code_type失败，失败原因：{}",e.getMessage());
        }
    }
}
