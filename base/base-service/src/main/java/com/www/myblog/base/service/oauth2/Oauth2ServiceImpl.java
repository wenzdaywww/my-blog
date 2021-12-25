package com.www.myblog.base.service.oauth2;

import com.www.common.config.oauth2.IOauth2Service;
import com.www.common.pojo.ScopeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/24 23:06 </p>
 */
@Service
public class Oauth2ServiceImpl implements IOauth2Service {
    /**
     * <p>@Description 查询当前资源服务器的请求路径允许的scope范围 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 22:46 </p>
     * @return java.util.List<com.www.myblog.common.pojo.ScopeDTO>
     */
    @Override
    public List<ScopeDTO> findUrlScope() {
        List<ScopeDTO> list = new ArrayList<>();
        ScopeDTO scopeDTO1 = new ScopeDTO();
        scopeDTO1.setUrl("/menu/**").setScope("base:write,base:read");
        list.add(scopeDTO1);
        ScopeDTO scopeDTO2 = new ScopeDTO();
        scopeDTO2.setUrl("/user/**").setScope("base:write,base:read");
        list.add(scopeDTO2);
        return list;
    }
}
