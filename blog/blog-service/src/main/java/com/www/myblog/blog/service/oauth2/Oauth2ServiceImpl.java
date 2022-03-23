package com.www.myblog.blog.service.oauth2;

import com.www.common.config.oauth2.resource.inf.IOauth2Service;
import com.www.common.pojo.dto.security.ScopeDTO;
import com.www.myblog.blog.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IRedisService redisService;

    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    @Override
    public List<ScopeDTO> findUrlScope() {
        return redisService.findUrlScope();
    }
}
