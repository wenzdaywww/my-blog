package com.www.authorise.config.handler;

import com.www.myblog.common.pojo.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 22:43 </p>
 */
@Slf4j
public class ResponseExceptionHandler implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        log.error("=====> 认证服务器异常",e);
        ResponseDTO<String> response = resolveException(e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
    /**
     * <p>@Description 构建返回异常 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/20 22:42 </p>
     * @param e
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    private ResponseDTO<String> resolveException(Exception e) {
        String msg = ResponseDTO.RespEnum.UNAUTHORIZED.getMsg();
        //不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            msg = "不支持的认证方式";
            //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            msg = "用户名或密码异常";
        }
        ResponseDTO<String> failResponse = new ResponseDTO<>(ResponseDTO.RespEnum.UNAUTHORIZED,msg);
        return failResponse;
    }
}
