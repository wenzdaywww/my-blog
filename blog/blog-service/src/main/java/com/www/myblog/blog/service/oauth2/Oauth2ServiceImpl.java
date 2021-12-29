package com.www.myblog.blog.service.oauth2;

import com.www.common.config.oauth2.IOauth2Service;
import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.constants.RedisCommonContant;
import com.www.common.pojo.dto.ScopeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/24 23:06 </p>
 */
@Slf4j
@Service
public class Oauth2ServiceImpl implements IOauth2Service {
    /** 资源服务id **/
    @Value("${spring.application.name}")
    private String resourceId;
    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    @Override
    public List<ScopeDTO> findUrlScope() {
        List<ScopeDTO> list = (List<ScopeDTO>) RedisOperation.listGet(RedisCommonContant.URL_SCOPE_PREFIX + resourceId);
        return list;
    }
}
