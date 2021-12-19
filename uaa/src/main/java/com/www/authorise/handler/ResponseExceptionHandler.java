package com.www.authorise.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 22:43 </p>
 */
public class ResponseExceptionHandler implements WebResponseExceptionTranslator {
    private static Logger LOG = LoggerFactory.getLogger(ResponseExceptionHandler.class);


    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        LOG.info("异常了。。。。");
        return null;
    }
}
