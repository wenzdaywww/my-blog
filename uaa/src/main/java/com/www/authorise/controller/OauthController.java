package com.www.authorise.controller;

import com.alibaba.fastjson.JSON;
import com.www.authorise.config.oauth2.Oauth2TokenConverter;
import com.www.common.pojo.ResponseDTO;
import com.www.common.pojo.TokenDTO;
import com.www.common.pojo.TokenInfoDTO;
import com.www.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * <p>@Description 重写oauth请求 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 23:36 </p>
 */
@Slf4j
@RestController
public class OauthController {
    /** redis的key分隔符 **/
    private static final String SEPARATOR = ":";
    /** redis的key前缀 **/
    private static final String PREFIX = "oauth2_token" + SEPARATOR;
    /** oauth/token请求的username **/
    private static final String USERNAME = "username";
    /** 保存到cookie的access_token的key **/
    private static final String COOKIES_ACCESS_TOKEN = "access_token";
    /** 保存到cookie的refresh_token的key **/
    private static final String COOKIES_REFRESH_TOKEN = "refresh_token";
    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private Oauth2TokenConverter oauth2TokenConverter;

    /**
     * <p>@Description 重写oauth/token的get请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @GetMapping("oauth/token")
    public ResponseDTO<TokenDTO> tokenGet(HttpServletResponse response, Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        return tokenPost(response,principal,parameters);
    }
    /**
     * <p>@Description 重写oauth/token的post请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @PostMapping("oauth/token")
    public ResponseDTO<TokenDTO> tokenPost(HttpServletResponse response, Principal principal, @RequestParam Map<String, String> parameters) throws Exception  {
        ResponseDTO<TokenDTO> responseDTO = new ResponseDTO<>();
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(accessToken.getBody().getValue());
        tokenDTO.setRefreshToken(accessToken.getBody().getRefreshToken() != null ?
                accessToken.getBody().getRefreshToken().getValue() : null);
        tokenDTO.setTokenType(accessToken.getBody().getTokenType());
        tokenDTO.setExpiresSeconds(accessToken.getBody().getExpiresIn());
        tokenDTO.setScope(accessToken.getBody().getScope());
        //解析token信息
        TokenInfoDTO tokenInfoDTO = oauth2TokenConverter.decodeToken(tokenDTO.getAccessToken());
        tokenDTO.setUserId(tokenInfoDTO != null ? tokenInfoDTO.getUser_name() : null);
        //将token保存到cookie中
        Cookie tokenCookie = new Cookie(COOKIES_ACCESS_TOKEN,tokenDTO.getAccessToken());
        tokenCookie.setMaxAge(tokenDTO.getExpiresSeconds());
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
        // 有刷新令牌则也保存到cookie中
        if(tokenDTO.getRefreshToken() != null){
            Cookie refreshCookie = new Cookie(COOKIES_REFRESH_TOKEN,tokenDTO.getRefreshToken());
            refreshCookie.setPath("/");
            response.addCookie(refreshCookie);
        }
        responseDTO.setResponseCode(ResponseDTO.RespEnum.SUCCESS,tokenDTO);
        //将token保存到redis中
        String key = PREFIX + parameters.get(OAuth2Utils.CLIENT_ID) + SEPARATOR + parameters.get(OAuth2Utils.GRANT_TYPE);
        if(StringUtils.isNotBlank(parameters.get(OAuth2Utils.SCOPE))){
            key += SEPARATOR + parameters.get(OAuth2Utils.SCOPE);
        }
        if(StringUtils.isNotBlank(parameters.get(USERNAME))){
            key += SEPARATOR + parameters.get(USERNAME);
        }
//        RedisUtils.set(key,tokenDTO.getAccessToken(),tokenDTO.getExpiresSeconds());
        log.info("=====> oauth/token返回的token信息：{}", JSON.toJSONString(tokenDTO));
        return responseDTO;
    }
}
