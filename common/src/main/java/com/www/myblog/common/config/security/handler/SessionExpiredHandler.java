package com.www.myblog.common.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 会话过期处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/18 21:05 </p>
 */
//@Component
public class SessionExpiredHandler implements SessionInformationExpiredStrategy {
    private static Logger LOG = LoggerFactory.getLogger(SessionExpiredHandler.class);

    /**
     * <p>@Description 单点登录会话过期处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/18 21:06 </p>
     * @param sessionInformationExpiredEvent
     * @return void
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        LOG.info("=====> 6、security会话过期");
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,"账号被挤下线");
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
