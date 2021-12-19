package com.www.authorise.controller;

import com.www.authorise.data.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * <p>@Description 重写oauth请求 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 23:36 </p>
 */
@RestController
@RequestMapping("oauth")
public class OauthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * <p>@Description 重写oauth/token的get请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @GetMapping("/token")
    public ResponseDTO<Object> tokenGet(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return tokenPost(principal,parameters);
    }
    /**
     * <p>@Description 重写oauth/token的post请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @PostMapping("/token")
    public ResponseDTO<Object> tokenPost(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,accessToken);
    }
}
